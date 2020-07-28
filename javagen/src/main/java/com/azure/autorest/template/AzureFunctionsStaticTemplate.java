package com.azure.autorest.template;


// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for license information.


import com.azure.autorest.extension.base.plugin.JavaSettings;
import com.azure.autorest.model.clientmodel.PackageInfo;
import com.azure.autorest.model.javamodel.JavaFile;

/**
 * Writes a PackageInfo to a JavaFile.
 */
public class AzureFunctionsStaticTemplate implements IJavaTemplate<PackageInfo, JavaFile> {
    private static AzureFunctionsStaticTemplate _instance = new AzureFunctionsStaticTemplate();

    private AzureFunctionsStaticTemplate() {
    }

    public static AzureFunctionsStaticTemplate getInstance() {
        return _instance;
    }

    public final void write(String content, JavaFile javaFile) {
        javaFile.getContents().text(content);
    }

    @Override
    public void write(PackageInfo model, JavaFile context) {
    }
}
