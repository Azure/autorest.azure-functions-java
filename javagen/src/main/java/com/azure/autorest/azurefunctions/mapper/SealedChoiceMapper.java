package com.azure.autorest.azurefunctions.mapper;

import com.azure.autorest.azurefunctions.extension.base.model.codemodel.ChoiceValue;
import com.azure.autorest.azurefunctions.extension.base.model.codemodel.SealedChoiceSchema;
import com.azure.autorest.azurefunctions.extension.base.plugin.JavaSettings;
import com.azure.autorest.azurefunctions.util.CodeNamer;
import com.azure.autorest.azurefunctions.model.clientmodel.ClassType;
import com.azure.autorest.azurefunctions.model.clientmodel.ClientEnumValue;
import com.azure.autorest.azurefunctions.model.clientmodel.EnumType;
import com.azure.autorest.azurefunctions.model.clientmodel.IType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SealedChoiceMapper implements IMapper<SealedChoiceSchema, IType> {
    private static SealedChoiceMapper instance = new SealedChoiceMapper();
    Map<SealedChoiceSchema, IType> parsed = new HashMap<>();

    private SealedChoiceMapper() {
    }

    public static SealedChoiceMapper getInstance() {
        return instance;
    }

    @Override
    public IType map(SealedChoiceSchema enumType) {
        if (enumType == null) {
            return null;
        }

        JavaSettings settings = JavaSettings.getInstance();

        if (parsed.containsKey(enumType)) {
            return parsed.get(enumType);
        }
        IType _itype;
        String enumTypeName = enumType.getLanguage().getJava().getName();

        if (enumTypeName == null || enumTypeName.isEmpty() || enumTypeName.equals("enum")) {
            _itype = ClassType.String;
        } else {
            String enumSubpackage = (settings.isFluent() ? "" : settings.getModelsSubpackage());
            String enumPackage = settings.getPackage(enumSubpackage);

            enumTypeName = CodeNamer.getTypeName(enumTypeName);

            List<ClientEnumValue> enumValues = new ArrayList<>();
            for (ChoiceValue enumValue : enumType.getChoices()) {
                String memberName = CodeNamer.getEnumMemberName(enumValue.getValue());
                enumValues.add(new ClientEnumValue(memberName, enumValue.getValue()));
            }

            _itype = new EnumType.Builder()
                    .packageName(enumPackage)
                    .name(enumTypeName)
                    .expandable(false)
                    .values(enumValues)
                    .elementType(Mappers.getSchemaMapper().map(enumType.getChoiceType()))
                    .build();
            parsed.put(enumType, _itype);
        }

        return _itype;
    }
}
