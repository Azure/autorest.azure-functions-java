package com.azure.autorest.azurefunctions.model.javamodel;

import com.azure.autorest.azurefunctions.extension.base.plugin.JavaSettings;
import com.azure.autorest.azurefunctions.template.Templates;
import com.azure.autorest.azurefunctions.model.clientmodel.ClientException;
import com.azure.autorest.azurefunctions.model.clientmodel.ClientModel;
import com.azure.autorest.azurefunctions.model.clientmodel.ClientResponse;
import com.azure.autorest.azurefunctions.model.clientmodel.EnumType;
import com.azure.autorest.azurefunctions.model.clientmodel.Manager;
import com.azure.autorest.azurefunctions.model.clientmodel.MethodGroupClient;
import com.azure.autorest.azurefunctions.model.clientmodel.PackageInfo;
import com.azure.autorest.azurefunctions.model.clientmodel.PageDetails;
import com.azure.autorest.azurefunctions.model.clientmodel.ServiceClient;
import com.azure.autorest.azurefunctions.model.clientmodel.XmlSequenceWrapper;

import java.util.ArrayList;
import java.util.List;

public class JavaPackage {
    private JavaSettings settings;
    private ArrayList<JavaFile> javaFiles;
    private JavaFileFactory javaFileFactory;
    public JavaPackage() {
        this.settings = JavaSettings.getInstance();
        this.javaFiles = new ArrayList<JavaFile>();
        this.javaFileFactory = new JavaFileFactory(settings);
    }

    public ArrayList<JavaFile> getJavaFiles() {
        return javaFiles;
    }

    public final void addManager(String package_Keyword, String name, Manager model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getManagerTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addServiceClient(String package_Keyword, String name, ServiceClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getServiceClientTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addAsyncServiceClient(String packageKeyWord, String name, ServiceClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(packageKeyWord, name);
        Templates.getServiceAsyncClientTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public void addSyncServiceClient(String packageKeyWord, String name, ServiceClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(packageKeyWord, name);
        Templates.getServiceSyncClientTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addServiceClientInterface(String name, ServiceClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(settings.getPackage(), name);
        Templates.getServiceClientInterfaceTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addServiceClientBuilder(String name, ServiceClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(settings.getPackage(), name);
        Templates.getServiceClientBuilderTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addServiceClientBuilder(String package_Keyword, String name, ServiceClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getServiceClientBuilderTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addMethodGroup(String package_Keyword, String name, MethodGroupClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getMethodGroupTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addMethodGroupInterface(String name, MethodGroupClient model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(settings.getPackage(), name);
        Templates.getMethodGroupInterfaceTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addModel(String package_Keyword, String name, ClientModel model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getModelTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addException(String package_Keyword, String name, ClientException model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getExceptionTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addEnum(String package_Keyword, String name, EnumType model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getEnumTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addPage(String package_Keyword, String name, PageDetails model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getPageTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addClientResponse(String package_Keyword, String name, ClientResponse model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getResponseTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addXmlSequenceWrapper(String package_Keyword, String name, XmlSequenceWrapper model) {
        JavaFile javaFile = javaFileFactory.createSourceFile(package_Keyword, name);
        Templates.getXmlSequenceWrapperTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addPackageInfo(String package_Keyword, String name, PackageInfo model) {
        JavaFile javaFile = javaFileFactory.createEmptySourceFile(package_Keyword, name);
        Templates.getPackageInfoTemplate().write(model, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addAzureFunctionsStaticFile(String package_Keyword, String name, String content) {
        JavaFile javaFile = javaFileFactory.createEmptySourceFileWithNoType(package_Keyword, name);
        Templates.getAzureFunctionsStaticTemplate().write(content, javaFile);
        javaFiles.add(javaFile);
    }

    public final void addAzureFunctionsFile(String package_Keyword, String name, List operations) {
        JavaFile javaFile = javaFileFactory.createEmptySourceFile(package_Keyword, name.substring(0, 1).toUpperCase() + name.substring(1) + "Functions");
        Templates.getAzureFunctionsTemplate().write(operations, javaFile, name, package_Keyword);
        javaFiles.add(javaFile);
    }

    // TODO: POM?
//    public final void AddPom(PomTemplate pomTemplate)
//    {
//        StringBuilder pomContentsBuilder = new StringBuilder();
//        try (pomTemplate.TextWriter = new StringWriter(pomContentsBuilder))
//        {
//            pomTemplate.ExecuteAsync().GetAwaiter().GetResult();
//        }
//        javaFiles.add(new JavaFile("pom.xml", pomContentsBuilder.toString()));
//    }
}
