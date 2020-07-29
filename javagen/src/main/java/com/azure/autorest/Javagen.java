package com.azure.autorest;

import com.azure.autorest.extension.base.jsonrpc.Connection;
import com.azure.autorest.extension.base.model.codemodel.CodeModel;
import com.azure.autorest.extension.base.model.codemodel.Operation;
import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.extension.base.plugin.NewPlugin;
import com.azure.autorest.mapper.Mappers;
import com.azure.autorest.model.clientmodel.Client;
import com.azure.autorest.model.clientmodel.ClientModel;
import com.azure.autorest.model.clientmodel.EnumType;
import com.azure.autorest.model.javamodel.JavaFile;
import com.azure.autorest.model.javamodel.JavaPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Javagen extends NewPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(Javagen.class);
    public Javagen(Connection connection, String plugin, String sessionId) {
        super(connection, plugin, sessionId);
    }

    @Override
    public boolean processInternal() {
        List<String> allFiles = listInputs();
        List<String> files = allFiles.stream().filter(s -> s.contains("no-tags")).collect(Collectors.toList());
        if (files.size() != 1) {
            throw new RuntimeException(String.format("Generator received incorrect number of inputs: %s : %s}", files.size(), String.join(", ", files)));
        }

        try {
            // Step 1: Parse input yaml as CodeModel
            String file = readFile(files.get(0));
            Representer representer = new Representer() {
                @Override
                protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue,
                    Tag customTag) {
                    // if value of property is null, ignore it.
                    if (propertyValue == null) {
                        return null;
                    }
                    else {
                        return super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
                    }
                }
            };

            Yaml newYaml  = new Yaml(representer);
            CodeModel codeModel = newYaml.loadAs(file, CodeModel.class);

            // Step 2: Map
            Client client = Mappers.getClientMapper().map(codeModel);

            // Step 3: Write to templates
            JavaPackage javaPackage = new JavaPackage();

            // Client model
            for (ClientModel model : client.getModels()) {
                javaPackage.addModel(model.getPackage(), model.getName(), model);
            }

            // Get Routs
            List<Operation> opertations = codeModel.getOperationGroups().get(0).getOperations();
            Map<String, List<Operation>> classFiles =new HashMap<String, List<Operation>>();
            for(Operation o: opertations) {
                String className = o.getRequests().get(0).getProtocol().getHttp().getPath().split("/")[1];
                if(classFiles.get(className) != null) {
                    List list = classFiles.get(className);
                    list.add(o);
                } else {
                    List a = new ArrayList<Operation>();
                    a.add(o);
                    classFiles.put(className, a);
                }
            }

            // Enum
            for (EnumType enumType : client.getEnums()) {
                javaPackage.addEnum(enumType.getPackage(), enumType.getName(), enumType);
            }

            // Exception
//            for (ClientException exception : client.getExceptions()) {
//                javaPackage.addException(exception.getPackage(), exception.getName(), exception);
//            }

            for(String className: classFiles.keySet()) {
                javaPackage.addAzureFunctionsFile(JavaSettings.getInstance().getPackage(), className, classFiles.get((className)));
            }

            File staticFiles = new File("./azure-functions");
            String [] staticFilesNames = staticFiles.list();
            for(String a: staticFilesNames) {
                String content = readFile("../azure-functions/" + a);
                javaPackage.addAzureFunctionsStaticFile(JavaSettings.getInstance().getPackage(), a, content);
            }



            //Step 4: Print to files
            for (JavaFile javaFile : javaPackage.getJavaFiles()) {
                writeFile(javaFile.getFilePath(), javaFile.getContents().toString(), null);
            }

        } catch (Exception ex) {
            LOGGER.error("Failed to generate code " + ex.getMessage(), ex);
            connection.sendError(1, 500, "Failed to generate code: " + ex.getMessage());
            return false;
        }
        return true;
    }
}
