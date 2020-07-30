package com.azure.autorest.template;


// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.


import com.azure.autorest.extension.base.model.codemodel.Operation;
import com.azure.autorest.model.clientmodel.*;
import com.azure.autorest.model.javamodel.JavaFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Writes a PackageInfo to a JavaFile.
 */
public class AzureFunctionsTemplate implements IJavaTemplate<PackageInfo, JavaFile> {
    private static AzureFunctionsTemplate _instance = new AzureFunctionsTemplate();

    private AzureFunctionsTemplate() {
    }

    public static AzureFunctionsTemplate getInstance() {
        return _instance;
    }

    public final void write(List<Operation> opertations, JavaFile javaFile, String className, String packageString) {
        Set<String> imports = new HashSet<String>();
        imports.add("com.microsoft.azure.functions.ExecutionContext");
        imports.add("com.microsoft.azure.functions.HttpMethod");
        imports.add("com.microsoft.azure.functions.HttpRequestMessage");
        imports.add("com.microsoft.azure.functions.HttpResponseMessage");
        imports.add("com.microsoft.azure.functions.HttpStatus");
        imports.add("com.microsoft.azure.functions.annotation.AuthorizationLevel");
        imports.add("com.microsoft.azure.functions.annotation.FunctionName");
        imports.add("com.microsoft.azure.functions.annotation.HttpTrigger");
        imports.add("java.util.Optional");

        javaFile.declarePackage(packageString);
        javaFile.getContents().line();
        javaFile.declareImport(imports);

        javaFile.javadocComment((comment) ->
        {
            comment.description(String.format("Azure Functions with HTTP Trigger."));
        });

        String classNameCapital = className.substring(0, 1).toUpperCase() + className.substring(1) + "Functions";
        javaFile.line("public class " + classNameCapital + " {");

            for(Operation o: opertations) {
                javaFile.line();
                javaFile.text("\t");
                javaFile.annotation("FunctionName(\"" + o.getLanguage().getJava().getName() +"\")");
                javaFile.text("\t");
                javaFile.line("public HttpResponseMessage " + o.getLanguage().getJava().getName() +"(");
                javaFile.text("\t");
                javaFile.line("@HttpTrigger(name = \"req\", methods = {HttpMethod." + o.getRequests().get(0).getProtocol().getHttp().getMethod().toUpperCase() + "}, authLevel = AuthorizationLevel.ANONYMOUS, route = \"" + o.getRequests().get(0).getProtocol().getHttp().getPath().substring(1) + "\" ) HttpRequestMessage<Optional<String>> request,");
                javaFile.text("\t");
                javaFile.line("final ExecutionContext context) {");

                javaFile.text("\t");
                javaFile.line("\t return request.createResponseBuilder(HttpStatus.OK).body(\"Success!\").build();\n\t}");
            }
        javaFile.line("}");
    }

    @Override
    public void write(PackageInfo model, JavaFile context) {
    }
}
