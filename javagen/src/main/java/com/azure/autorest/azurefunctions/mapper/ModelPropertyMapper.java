package com.azure.autorest.azurefunctions.mapper;

import com.azure.autorest.azurefunctions.extension.base.model.codemodel.ConstantSchema;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.XmlSerlializationFormat;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.ArraySchema;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.Property;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.Schema;
import com.azure.autorest.azurefunctions.model.clientmodel.ClientModelProperty;
import com.azure.autorest.azurefunctions.model.clientmodel.IType;
import java.util.ArrayList;
import java.util.List;

public class ModelPropertyMapper implements IMapper<Property, ClientModelProperty> {
    private static ModelPropertyMapper instance = new ModelPropertyMapper();

    private ModelPropertyMapper() {
    }

    public static ModelPropertyMapper getInstance() {
        return instance;
    }

    @Override
    public ClientModelProperty map(Property property) {
        ClientModelProperty.Builder builder = new ClientModelProperty.Builder()
                .name(property.getLanguage().getJava().getName())
                .isRequired(property.isRequired())
                .isReadOnly(property.isReadOnly());

        if (property.getLanguage().getJava().getDescription() == null || property.getLanguage().getJava().getDescription().isEmpty()) {
            builder.description(String.format("The %s property.", property.getSerializedName()));
        } else {
            builder.description(property.getLanguage().getJava().getDescription());
        }

        boolean flattened = false;
        if (property.getParentSchema() != null) {
            flattened = property.getParentSchema().getProperties().stream()
                    .anyMatch(p -> p.getFlattenedNames() != null && !p.getFlattenedNames().isEmpty());
        }

        StringBuilder serializedName = new StringBuilder();
        if (property.getFlattenedNames() != null && !property.getFlattenedNames().isEmpty()) {
            for (String flattenedName : property.getFlattenedNames()) {
                serializedName.append(flattenedName.replace(".", "\\\\.")).append(".");
            }
            serializedName.deleteCharAt(serializedName.length() - 1);
        } else if (flattened) {
            serializedName.append(property.getSerializedName().replace(".", "\\\\."));
        } else {
            serializedName.append(property.getSerializedName());
        }
        builder.serializedName(serializedName.toString());
        if (serializedName.toString().isEmpty() && "additionalProperties".equals(property.getLanguage().getJava().getName())) {
            builder.isAdditionalProperties(true);
        }

        XmlSerlializationFormat xmlSerlializationFormat = null;
        if (property.getSchema().getSerialization() != null) {
            xmlSerlializationFormat = property.getSchema().getSerialization().getXml();
        }

        String xmlName = null;
        boolean isXmlWrapper = false;
        boolean isXmlAttribute = false;
        if (xmlSerlializationFormat != null) {
            isXmlWrapper = xmlSerlializationFormat.isWrapped();
            isXmlAttribute = xmlSerlializationFormat.isAttribute();
            xmlName = xmlSerlializationFormat.getName();
        }

        final String xmlParamName = xmlName == null ? serializedName.toString() : xmlName;
        builder.xmlName(xmlParamName).isXmlWrapper(isXmlWrapper).isXmlAttribute(isXmlAttribute);

        List<String> annotationArgumentList = new ArrayList<String>() {{
            add(String.format("value = \"%s\"", xmlParamName));
        }};
        if (property.isRequired()) {
            annotationArgumentList.add("required = true");
        }
        if (property.isReadOnly()) {
            annotationArgumentList.add("access = JsonProperty.Access.WRITE_ONLY");
        }
        builder.annotationArguments(String.join(", ", annotationArgumentList));

//        String headerCollectionPrefix = property.Extensions?.GetValue<string>(SwaggerExtensions.HeaderCollectionPrefix);

        IType propertyWireType = Mappers.getSchemaMapper().map(property.getSchema());
        if (propertyWireType != null && property.isNullable()) {
            propertyWireType = propertyWireType.asNullable();
        }

        IType propertyClientType = Mappers.getSchemaMapper().map((property.getSchema())).getClientType();

        if (!property.isRequired()) {
            propertyClientType = propertyClientType.asNullable();
            propertyWireType = propertyWireType.asNullable();
        }
        builder.wireType(propertyWireType).clientType(propertyClientType);

        Schema autoRestPropertyModelType = property.getSchema();
        if (autoRestPropertyModelType instanceof ArraySchema) {
            ArraySchema sequence = (ArraySchema) autoRestPropertyModelType;
            if (sequence.getElementType().getSerialization() != null
                && sequence.getElementType().getSerialization().getXml() != null
                && sequence.getElementType().getSerialization().getXml().getName() != null) {
                builder.xmlListElementName(sequence.getElementType().getSerialization().getXml().getName());
            } else if(sequence.getSerialization() != null && sequence.getSerialization().getXml() != null){
                builder.xmlListElementName(sequence.getSerialization().getXml().getName());
            }
        }

        if (property.getSchema() instanceof ConstantSchema) {
            Object objValue = ((ConstantSchema) property.getSchema()).getValue().getValue();
            builder.isConstant(true);
            builder.defaultValue(objValue == null ? null : propertyClientType.defaultValueExpression(String.valueOf(objValue)));
        }

        return builder.build();
    }
}
