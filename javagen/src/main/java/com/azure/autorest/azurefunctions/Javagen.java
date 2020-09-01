package com.azure.autorest.azurefunctions;

import com.azure.autorest.azurefunctions.extension.base.jsonrpc.Connection;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.CodeModel;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.Operation;
import com.azure.autorest.azurefunctions.extension.base.plugin.JavaSettings;
import com.azure.autorest.azurefunctions.extension.base.plugin.NewPlugin;
import com.azure.autorest.azurefunctions.mapper.Mappers;
import com.azure.autorest.azurefunctions.model.clientmodel.Client;
import com.azure.autorest.azurefunctions.model.clientmodel.ClientModel;
import com.azure.autorest.azurefunctions.model.clientmodel.EnumType;
import com.azure.autorest.azurefunctions.model.javamodel.JavaFile;
import com.azure.autorest.azurefunctions.model.javamodel.JavaPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

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
                if (classFiles.get(className) != null) {
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
            Map<String,String> staticFiles = new HashMap<String,String>();
            staticFiles.put("pom.xml", String.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                    "    <modelVersion>4.0.0</modelVersion>\n" +
                    "\n" +
                    "    <groupId>%s</groupId>\n" +
                    "    <artifactId>%s</artifactId>\n" +
                    "    <version>1.0-SNAPSHOT</version>\n" +
                    "    <packaging>jar</packaging>\n" +
                    "\n" +
                    "    <name>Azure Java Functions</name>\n" +
                    "\n" +
                    "    <properties>\n" +
                    "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
                    "        <java.version>1.8</java.version>\n" +
                    "        <azure.functions.maven.plugin.version>1.6.0</azure.functions.maven.plugin.version>\n" +
                    "        <azure.functions.java.library.version>1.3.1</azure.functions.java.library.version>\n" +
                    "        <functionAppName>%s</functionAppName>\n" +
                    "        <stagingDirectory>${project.build.directory}/azure-functions/${functionAppName}</stagingDirectory>\n" +
                    "    </properties>\n" +
                    "\n" +
                    "    <dependencies>\n" +
                    "        <dependency>\n" +
                    "            <groupId>com.microsoft.azure.functions</groupId>\n" +
                    "            <artifactId>azure-functions-java-library</artifactId>\n" +
                    "            <version>${azure.functions.java.library.version}</version>\n" +
                    "        </dependency>\n" +
                    "\n" +
                    "        <dependency>\n" +
                    "            <groupId>com.fasterxml.jackson.core</groupId>\n" +
                    "            <artifactId>jackson-annotations</artifactId>\n" +
                    "            <version>2.11.1</version>\n" +
                    "        </dependency>\n" +
                    "\n" +
                    "        <dependency>\n" +
                    "            <groupId>com.azure</groupId>\n" +
                    "            <artifactId>azure-core</artifactId>\n" +
                    "            <version>1.6.0</version>\n" +
                    "        </dependency>\n" +
                    "\n" +
                    "        <!-- Test -->\n" +
                    "        <dependency>\n" +
                    "            <groupId>org.junit.jupiter</groupId>\n" +
                    "            <artifactId>junit-jupiter</artifactId>\n" +
                    "            <version>5.4.2</version>\n" +
                    "            <scope>test</scope>\n" +
                    "        </dependency>\n" +
                    "\n" +
                    "        <dependency>\n" +
                    "            <groupId>org.mockito</groupId>\n" +
                    "            <artifactId>mockito-core</artifactId>\n" +
                    "            <version>2.23.4</version>\n" +
                    "            <scope>test</scope>\n" +
                    "        </dependency>\n" +
                    "    </dependencies>\n" +
                    "\n" +
                    "    <build>\n" +
                    "        <plugins>\n" +
                    "            <plugin>\n" +
                    "                <groupId>org.apache.maven.plugins</groupId>\n" +
                    "                <artifactId>maven-compiler-plugin</artifactId>\n" +
                    "                <version>3.8.1</version>\n" +
                    "                <configuration>\n" +
                    "                    <source>${java.version}</source>\n" +
                    "                    <target>${java.version}</target>\n" +
                    "                    <encoding>${project.build.sourceEncoding}</encoding>\n" +
                    "                </configuration>\n" +
                    "            </plugin>\n" +
                    "            <plugin>\n" +
                    "                <groupId>com.microsoft.azure</groupId>\n" +
                    "                <artifactId>azure-functions-maven-plugin</artifactId>\n" +
                    "                <version>${azure.functions.maven.plugin.version}</version>\n" +
                    "                <configuration>\n" +
                    "                    <!-- function app name -->\n" +
                    "                    <appName>${functionAppName}</appName>\n" +
                    "                    <!-- function app resource group -->\n" +
                    "                    <resourceGroup>mamoun-java-11-windows</resourceGroup>\n" +
                    "                    <!-- function app service plan name -->\n" +
                    "                    <appServicePlanName>ASP-mamounjava11-80eb-windows</appServicePlanName>\n" +
                    "                    <!-- function app region-->\n" +
                    "                    <!-- refers https://github.com/microsoft/azure-maven-plugins/wiki/Azure-Functions:-Configuration-Details#supported-regions for all valid values -->\n" +
                    "                    <region>westus</region>\n" +
                    "                    <!-- function pricingTier, default to be consumption if not specified -->\n" +
                    "                    <!-- refers https://github.com/microsoft/azure-maven-plugins/wiki/Azure-Functions:-Configuration-Details#supported-pricing-tiers for all valid values -->\n" +
                    "                    <!-- <pricingTier></pricingTier> -->\n" +
                    "                    \n" +
                    "                    <!-- Whether to disable application insights, default is false -->\n" +
                    "                    <!-- refers https://github.com/microsoft/azure-maven-plugins/wiki/Azure-Functions:-Configuration-Details for all valid configurations for application insights-->\n" +
                    "                    <!-- <disableAppInsights></disableAppInsights> -->\n" +
                    "                    <runtime>\n" +
                    "                        <!-- runtime os, could be windows, linux or docker-->\n" +
                    "                        <os>windows</os>\n" +
                    "                        <javaVersion>11</javaVersion>\n" +
                    "                        <!-- for docker function, please set the following parameters -->\n" +
                    "                        <!-- <image>[hub-user/]repo-name[:tag]</image> -->\n" +
                    "                        <!-- <serverId></serverId> -->\n" +
                    "                        <!-- <registryUrl></registryUrl>  -->\n" +
                    "                    </runtime>\n" +
                    "                    <appSettings>\n" +
                    "                        <property>\n" +
                    "                            <name>FUNCTIONS_EXTENSION_VERSION</name>\n" +
                    "                            <value>~3</value>\n" +
                    "                        </property>\n" +
                    "                    </appSettings>\n" +
                    "                </configuration>\n" +
                    "                <executions>\n" +
                    "                    <execution>\n" +
                    "                        <id>package-functions</id>\n" +
                    "                        <goals>\n" +
                    "                            <goal>package</goal>\n" +
                    "                        </goals>\n" +
                    "                    </execution>\n" +
                    "                </executions>\n" +
                    "            </plugin>\n" +
                    "            <plugin>\n" +
                    "                <groupId>org.apache.maven.plugins</groupId>\n" +
                    "                <artifactId>maven-resources-plugin</artifactId>\n" +
                    "                <version>3.1.0</version>\n" +
                    "                <executions>\n" +
                    "                    <execution>\n" +
                    "                        <id>copy-resources</id>\n" +
                    "                        <phase>package</phase>\n" +
                    "                        <goals>\n" +
                    "                            <goal>copy-resources</goal>\n" +
                    "                        </goals>\n" +
                    "                        <configuration>\n" +
                    "                            <overwrite>true</overwrite>\n" +
                    "                            <outputDirectory>${stagingDirectory}</outputDirectory>\n" +
                    "                            <resources>\n" +
                    "                                <resource>\n" +
                    "                                    <directory>${project.basedir}</directory>\n" +
                    "                                    <includes>\n" +
                    "                                        <include>host.json</include>\n" +
                    "                                        <include>local.settings.json</include>\n" +
                    "                                    </includes>\n" +
                    "                                </resource>\n" +
                    "                            </resources>\n" +
                    "                        </configuration>\n" +
                    "                    </execution>\n" +
                    "                </executions>\n" +
                    "            </plugin>\n" +
                    "            <plugin>\n" +
                    "                <groupId>org.apache.maven.plugins</groupId>\n" +
                    "                <artifactId>maven-dependency-plugin</artifactId>\n" +
                    "                <version>3.1.1</version>\n" +
                    "                <executions>\n" +
                    "                    <execution>\n" +
                    "                        <id>copy-dependencies</id>\n" +
                    "                        <phase>prepare-package</phase>\n" +
                    "                        <goals>\n" +
                    "                            <goal>copy-dependencies</goal>\n" +
                    "                        </goals>\n" +
                    "                        <configuration>\n" +
                    "                            <outputDirectory>${stagingDirectory}/lib</outputDirectory>\n" +
                    "                            <overWriteReleases>false</overWriteReleases>\n" +
                    "                            <overWriteSnapshots>false</overWriteSnapshots>\n" +
                    "                            <overWriteIfNewer>true</overWriteIfNewer>\n" +
                    "                            <includeScope>runtime</includeScope>\n" +
                    "                            <excludeArtifactIds>azure-functions-java-library</excludeArtifactIds>\n" +
                    "                        </configuration>\n" +
                    "                    </execution>\n" +
                    "                </executions>\n" +
                    "            </plugin>\n" +
                    "            <!--Remove obj folder generated by .NET SDK in maven clean-->\n" +
                    "            <plugin>\n" +
                    "                <artifactId>maven-clean-plugin</artifactId>\n" +
                    "                <version>3.1.0</version>\n" +
                    "                <configuration>\n" +
                    "                    <filesets>\n" +
                    "                        <fileset>\n" +
                    "                            <directory>obj</directory>\n" +
                    "                        </fileset>\n" +
                    "                    </filesets>\n" +
                    "                </configuration>\n" +
                    "            </plugin>\n" +
                    "        </plugins>\n" +
                    "    </build>\n" +
                    "</project>\n", JavaSettings.getInstance().getPackage(), client.getClientName(), client.getClientName()));
            if(getStringValue("generate-metadata") == null || getStringValue("generate-metadata").toLowerCase()!="false") {
                staticFiles.put("host.json", "{\n" +
                        "    \"version\": \"2.0\",\n" +
                        "    \"logging\": {\n" +
                        "        \"applicationInsights\": {\n" +
                        "            \"samplingExcludedTypes\": \"Request\",\n" +
                        "            \"samplingSettings\": {\n" +
                        "                \"isEnabled\": true\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
                staticFiles.put("local.settings.json", "{\n" +
                        "    \"IsEncrypted\": false,\n" +
                        "    \"Values\": {\n" +
                        "        \"AzureWebJobsStorage\": \"UseDevelopmentStorage=true\",\n" +
                        "        \"FUNCTIONS_WORKER_RUNTIME\": \"java\"\n" +
                        "    }\n" +
                        "}");

                staticFiles.put(".gitignore", "{% raw %}\n" +
                        "## Ignore Visual Studio temporary files, build results, and\n" +
                        "## files generated by popular Visual Studio add-ons.\n" +
                        "\n" +
                        "# Azure Functions localsettings file\n" +
                        "local.settings.json\n" +
                        "\n" +
                        "# User-specific files\n" +
                        "*.suo\n" +
                        "*.user\n" +
                        "*.userosscache\n" +
                        "*.sln.docstates\n" +
                        "\n" +
                        "# User-specific files (MonoDevelop/Xamarin Studio)\n" +
                        "*.userprefs\n" +
                        "\n" +
                        "# Build results\n" +
                        "[Dd]ebug/\n" +
                        "[Dd]ebugPublic/\n" +
                        "[Rr]elease/\n" +
                        "[Rr]eleases/\n" +
                        "x64/\n" +
                        "x86/\n" +
                        "bld/\n" +
                        "[Bb]in/\n" +
                        "[Oo]bj/\n" +
                        "[Ll]og/\n" +
                        "\n" +
                        "# Visual Studio 2015 cache/options directory\n" +
                        ".vs/\n" +
                        "# Uncomment if you have tasks that create the project's static files in wwwroot\n" +
                        "#wwwroot/\n" +
                        "\n" +
                        "# MSTest test Results\n" +
                        "[Tt]est[Rr]esult*/\n" +
                        "[Bb]uild[Ll]og.*\n" +
                        "\n" +
                        "# NUNIT\n" +
                        "*.VisualState.xml\n" +
                        "TestResult.xml\n" +
                        "\n" +
                        "# Build Results of an ATL Project\n" +
                        "[Dd]ebugPS/\n" +
                        "[Rr]eleasePS/\n" +
                        "dlldata.c\n" +
                        "\n" +
                        "# DNX\n" +
                        "project.lock.json\n" +
                        "project.fragment.lock.json\n" +
                        "artifacts/\n" +
                        "\n" +
                        "*_i.c\n" +
                        "*_p.c\n" +
                        "*_i.h\n" +
                        "*.ilk\n" +
                        "*.meta\n" +
                        "*.obj\n" +
                        "*.pch\n" +
                        "*.pdb\n" +
                        "*.pgc\n" +
                        "*.pgd\n" +
                        "*.rsp\n" +
                        "*.sbr\n" +
                        "*.tlb\n" +
                        "*.tli\n" +
                        "*.tlh\n" +
                        "*.tmp\n" +
                        "*.tmp_proj\n" +
                        "*.log\n" +
                        "*.vspscc\n" +
                        "*.vssscc\n" +
                        ".builds\n" +
                        "*.pidb\n" +
                        "*.svclog\n" +
                        "*.scc\n" +
                        "\n" +
                        "# Chutzpah Test files\n" +
                        "_Chutzpah*\n" +
                        "\n" +
                        "# Visual C++ cache files\n" +
                        "ipch/\n" +
                        "*.aps\n" +
                        "*.ncb\n" +
                        "*.opendb\n" +
                        "*.opensdf\n" +
                        "*.sdf\n" +
                        "*.cachefile\n" +
                        "*.VC.db\n" +
                        "*.VC.VC.opendb\n" +
                        "\n" +
                        "# Visual Studio profiler\n" +
                        "*.psess\n" +
                        "*.vsp\n" +
                        "*.vspx\n" +
                        "*.sap\n" +
                        "\n" +
                        "# TFS 2012 Local Workspace\n" +
                        "$tf/\n" +
                        "\n" +
                        "# Guidance Automation Toolkit\n" +
                        "*.gpState\n" +
                        "\n" +
                        "# ReSharper is a .NET coding add-in\n" +
                        "_ReSharper*/\n" +
                        "*.[Rr]e[Ss]harper\n" +
                        "*.DotSettings.user\n" +
                        "\n" +
                        "# JustCode is a .NET coding add-in\n" +
                        ".JustCode\n" +
                        "\n" +
                        "# TeamCity is a build add-in\n" +
                        "_TeamCity*\n" +
                        "\n" +
                        "# DotCover is a Code Coverage Tool\n" +
                        "*.dotCover\n" +
                        "\n" +
                        "# NCrunch\n" +
                        "_NCrunch_*\n" +
                        ".*crunch*.local.xml\n" +
                        "nCrunchTemp_*\n" +
                        "\n" +
                        "# MightyMoose\n" +
                        "*.mm.*\n" +
                        "AutoTest.Net/\n" +
                        "\n" +
                        "# Web workbench (sass)\n" +
                        ".sass-cache/\n" +
                        "\n" +
                        "# Installshield output folder\n" +
                        "[Ee]xpress/\n" +
                        "\n" +
                        "# DocProject is a documentation generator add-in\n" +
                        "DocProject/buildhelp/\n" +
                        "DocProject/Help/*.HxT\n" +
                        "DocProject/Help/*.HxC\n" +
                        "DocProject/Help/*.hhc\n" +
                        "DocProject/Help/*.hhk\n" +
                        "DocProject/Help/*.hhp\n" +
                        "DocProject/Help/Html2\n" +
                        "DocProject/Help/html\n" +
                        "\n" +
                        "# Click-Once directory\n" +
                        "publish/\n" +
                        "\n" +
                        "# Publish Web Output\n" +
                        "*.[Pp]ublish.xml\n" +
                        "*.azurePubxml\n" +
                        "# TODO: Comment the next line if you want to checkin your web deploy settings\n" +
                        "# but database connection strings (with potential passwords) will be unencrypted\n" +
                        "#*.pubxml\n" +
                        "*.publishproj\n" +
                        "\n" +
                        "# Microsoft Azure Web App publish settings. Comment the next line if you want to\n" +
                        "# checkin your Azure Web App publish settings, but sensitive information contained\n" +
                        "# in these scripts will be unencrypted\n" +
                        "PublishScripts/\n" +
                        "\n" +
                        "# NuGet Packages\n" +
                        "*.nupkg\n" +
                        "# The packages folder can be ignored because of Package Restore\n" +
                        "**/packages/*\n" +
                        "# except build/, which is used as an MSBuild target.\n" +
                        "!**/packages/build/\n" +
                        "# Uncomment if necessary however generally it will be regenerated when needed\n" +
                        "#!**/packages/repositories.config\n" +
                        "# NuGet v3's project.json files produces more ignoreable files\n" +
                        "*.nuget.props\n" +
                        "*.nuget.targets\n" +
                        "\n" +
                        "# Microsoft Azure Build Output\n" +
                        "csx/\n" +
                        "*.build.csdef\n" +
                        "\n" +
                        "# Microsoft Azure Emulator\n" +
                        "ecf/\n" +
                        "rcf/\n" +
                        "\n" +
                        "# Windows Store app package directories and files\n" +
                        "AppPackages/\n" +
                        "BundleArtifacts/\n" +
                        "Package.StoreAssociation.xml\n" +
                        "_pkginfo.txt\n" +
                        "\n" +
                        "# Visual Studio cache files\n" +
                        "# files ending in .cache can be ignored\n" +
                        "*.[Cc]ache\n" +
                        "# but keep track of directories ending in .cache\n" +
                        "!*.[Cc]ache/\n" +
                        "\n" +
                        "# Others\n" +
                        "ClientBin/\n" +
                        "~$*\n" +
                        "*~\n" +
                        "*.dbmdl\n" +
                        "*.dbproj.schemaview\n" +
                        "*.jfm\n" +
                        "*.pfx\n" +
                        "*.publishsettings\n" +
                        "node_modules/\n" +
                        "orleans.codegen.cs\n" +
                        "\n" +
                        "# Since there are multiple workflows, uncomment next line to ignore bower_components\n" +
                        "# (https://github.com/github/gitignore/pull/1529#issuecomment-104372622)\n" +
                        "#bower_components/\n" +
                        "\n" +
                        "# RIA/Silverlight projects\n" +
                        "Generated_Code/\n" +
                        "\n" +
                        "# Backup & report files from converting an old project file\n" +
                        "# to a newer Visual Studio version. Backup files are not needed,\n" +
                        "# because we have git ;-)\n" +
                        "_UpgradeReport_Files/\n" +
                        "Backup*/\n" +
                        "UpgradeLog*.XML\n" +
                        "UpgradeLog*.htm\n" +
                        "\n" +
                        "# SQL Server files\n" +
                        "*.mdf\n" +
                        "*.ldf\n" +
                        "\n" +
                        "# Business Intelligence projects\n" +
                        "*.rdl.data\n" +
                        "*.bim.layout\n" +
                        "*.bim_*.settings\n" +
                        "\n" +
                        "# Microsoft Fakes\n" +
                        "FakesAssemblies/\n" +
                        "\n" +
                        "# GhostDoc plugin setting file\n" +
                        "*.GhostDoc.xml\n" +
                        "\n" +
                        "# Node.js Tools for Visual Studio\n" +
                        ".ntvs_analysis.dat\n" +
                        "\n" +
                        "# Visual Studio 6 build log\n" +
                        "*.plg\n" +
                        "\n" +
                        "# Visual Studio 6 workspace options file\n" +
                        "*.opt\n" +
                        "\n" +
                        "# Visual Studio LightSwitch build output\n" +
                        "**/*.HTMLClient/GeneratedArtifacts\n" +
                        "**/*.DesktopClient/GeneratedArtifacts\n" +
                        "**/*.DesktopClient/ModelManifest.xml\n" +
                        "**/*.Server/GeneratedArtifacts\n" +
                        "**/*.Server/ModelManifest.xml\n" +
                        "_Pvt_Extensions\n" +
                        "\n" +
                        "# Paket dependency manager\n" +
                        ".paket/paket.exe\n" +
                        "paket-files/\n" +
                        "\n" +
                        "# FAKE - F# Make\n" +
                        ".fake/\n" +
                        "\n" +
                        "# JetBrains Rider\n" +
                        ".idea/\n" +
                        "*.sln.iml\n" +
                        "\n" +
                        "# CodeRush\n" +
                        ".cr/\n" +
                        "\n" +
                        "# Python Tools for Visual Studio (PTVS)\n" +
                        "__pycache__/\n" +
                        "*.pyc\n" +
                        "{% endraw %}");
            }

            for(String a: staticFiles.keySet()) {
                javaPackage.addAzureFunctionsStaticFile(JavaSettings.getInstance().getPackage(), a, staticFiles.get(a));
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
