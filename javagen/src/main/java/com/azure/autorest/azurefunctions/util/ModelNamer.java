/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for license information.
 *
 */

package com.azure.autorest.azurefunctions.util;

import com.azure.autorest.azurefunctions.model.clientmodel.ClassType;
import com.azure.autorest.azurefunctions.model.clientmodel.ClientModelProperty;
import com.azure.autorest.azurefunctions.model.clientmodel.PrimitiveType;

public class ModelNamer {

    public String modelPropertyGetterName(ClientModelProperty property) {
        String prefix = "get";
        if (property.getClientType() == PrimitiveType.Boolean || property.getClientType() == ClassType.Boolean) {
            prefix = "is";
            if (CodeNamer.toCamelCase(property.getName()).startsWith(prefix)) {
                return CodeNamer.toCamelCase(property.getName());
            }
        }
        return prefix + CodeNamer.toPascalCase(property.getName());
    }

    public String modelPropertyGetterName(String propertyName) {
        return "get" + CodeNamer.toPascalCase(propertyName);
    }

    public String modelPropertySetterName(ClientModelProperty property) {
        return "set" + CodeNamer.toPascalCase(property.getName());
    }

    public String modelPropertySetterName(String propertyName) {
        return "set" + CodeNamer.toPascalCase(propertyName);
    }
}
