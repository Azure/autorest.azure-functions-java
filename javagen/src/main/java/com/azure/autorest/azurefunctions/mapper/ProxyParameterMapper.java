package com.azure.autorest.azurefunctions.mapper;

import com.azure.autorest.azurefunctions.extension.base.model.codemodel.ConstantSchema;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.Parameter;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.RequestParameterLocation;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.Schema;
import com.azure.autorest.azurefunctions.extension.base.plugin.JavaSettings;
import com.azure.autorest.azurefunctions.util.CodeNamer;
import com.azure.autorest.azurefunctions.model.clientmodel.ArrayType;
import com.azure.autorest.azurefunctions.model.clientmodel.ClassType;
import com.azure.autorest.azurefunctions.model.clientmodel.IType;
import com.azure.autorest.azurefunctions.model.clientmodel.ListType;
import com.azure.autorest.azurefunctions.model.clientmodel.PrimitiveType;
import com.azure.autorest.azurefunctions.model.clientmodel.ProxyMethodParameter;
import com.azure.core.util.serializer.CollectionFormat;

public class ProxyParameterMapper implements IMapper<Parameter, ProxyMethodParameter> {
    private static ProxyParameterMapper instance = new ProxyParameterMapper();

    private ProxyParameterMapper() {
    }

    public static ProxyParameterMapper getInstance() {
        return instance;
    }

    @Override
    public ProxyMethodParameter map(Parameter parameter) {
        JavaSettings settings = JavaSettings.getInstance();

        ProxyMethodParameter.Builder builder = new ProxyMethodParameter.Builder()
                .requestParameterName(parameter.getLanguage().getDefault().getSerializedName())
                .name(parameter.getLanguage().getJava().getName())
                .isRequired(parameter.isRequired())
                .isNullable(parameter.isNullable());

        //TODO: HeaderCollectionPrefix
//        String parameterHeaderCollectionPrefix = parameter.Extensions.GetValue<string>(SwaggerExtensions.HeaderCollectionPrefix);

        Schema ParameterJvWireType = parameter.getSchema();
        IType wireType = Mappers.getSchemaMapper().map(ParameterJvWireType);
        if (parameter.isNullable() || !parameter.isRequired()) {
            wireType = wireType.asNullable();
        }
        IType clientType = wireType.getClientType();
        builder.clientType(clientType);

        RequestParameterLocation parameterRequestLocation = parameter.getProtocol().getHttp().getIn();
        builder.requestParameterLocation(parameterRequestLocation);

        boolean parameterIsServiceClientProperty = parameter.getImplementation() == Parameter.ImplementationLocation.CLIENT;
        builder.fromClient(parameterIsServiceClientProperty);

        if (wireType instanceof ListType && settings.shouldGenerateXmlSerialization() && parameterRequestLocation == RequestParameterLocation.Body){
            String parameterTypePackage = settings.getPackage(settings.getImplementationSubpackage());
            String parameterTypeName = CodeNamer
                .toPascalCase(ParameterJvWireType.getSerialization().getXml().getName() +
                    "Wrapper");
            wireType = new ClassType.Builder()
                .packageName(parameterTypePackage)
                .name(parameterTypeName)
                .build();
        } else if (wireType == ArrayType.ByteArray) {
            if (parameterRequestLocation != RequestParameterLocation.Body /*&& parameterRequestLocation != RequestParameterLocation.FormData*/) {
                wireType = ClassType.String;
            }
        } else if (wireType instanceof ListType && parameter.getProtocol().getHttp().getIn() != RequestParameterLocation.Body /*&& parameter.getProtocol().getHttp().getIn() != RequestParameterLocation.FormData*/) {
            wireType = ClassType.String;
        }
        builder.wireType(wireType);

        String parameterDescription = parameter.getDescription();
        if (parameterDescription == null || parameterDescription.isEmpty()) {
            parameterDescription = String.format("the %s value", clientType);
        }
        builder.description(parameterDescription);

        if (parameter.getExtensions() != null) {
            builder.alreadyEncoded(parameter.getExtensions().isXmsSkipUrlEncoding());
        }

        if (parameter.getSchema() instanceof ConstantSchema){
            builder.isConstant(true);
            Object objValue = ((ConstantSchema) parameter.getSchema()).getValue().getValue();
            builder.defaultValue(objValue == null ? null : String.valueOf(objValue));
        }

        String parameterReference = parameter.getLanguage().getJava().getName();
        if (Parameter.ImplementationLocation.CLIENT.equals(parameter.getImplementation())) {
            String operationGroupName = parameter.getOperation().getOperationGroup().getLanguage().getJava().getName();
            String caller = (operationGroupName == null || operationGroupName.isEmpty()) ? "this" : "this.client";
            String clientPropertyName = parameter.getLanguage().getJava().getName();
            if (clientPropertyName != null && !clientPropertyName.isEmpty()) {
                clientPropertyName = CodeNamer.toPascalCase(CodeNamer.removeInvalidCharacters(clientPropertyName));
            }
            String prefix = "get";
            if (clientType == PrimitiveType.Boolean || clientType == ClassType.Boolean) {
                prefix = "is";
                if (CodeNamer.toCamelCase(parameterReference).startsWith(prefix)) {
                    prefix = "";
                    clientPropertyName = CodeNamer.toCamelCase(clientPropertyName);
                }
            }
            parameterReference = String.format("%s.%s%s()", caller, prefix, clientPropertyName);
        }
        builder.parameterReference(parameterReference);

        CollectionFormat collectionFormat = null;
        if (parameter.getProtocol().getHttp().getStyle() != null) {
            switch (parameter.getProtocol().getHttp().getStyle()) {
                case SIMPLE:
                    collectionFormat = CollectionFormat.CSV;
                    break;
                case SPACE_DELIMITED:
                    collectionFormat = CollectionFormat.SSV;
                    break;
                case PIPE_DELIMITED:
                    collectionFormat = CollectionFormat.PIPES;
                    break;
                case TAB_DELIMITED:
                    collectionFormat = CollectionFormat.TSV;
                    break;
                default:
                    collectionFormat = CollectionFormat.CSV;
            }
        }
        if (collectionFormat == null && clientType instanceof ListType
                && ClassType.String == wireType) {
            collectionFormat = CollectionFormat.CSV;
        }
        builder.collectionFormat(collectionFormat);

        return builder.build();
    }
}
