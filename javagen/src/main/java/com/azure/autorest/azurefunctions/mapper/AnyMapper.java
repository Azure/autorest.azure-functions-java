package com.azure.autorest.azurefunctions.mapper;

import com.azure.autorest.azurefunctions.extension.base.model.codemodel.AnySchema;
import com.azure.autorest.azurefunctions.model.clientmodel.ClassType;
import com.azure.autorest.azurefunctions.model.clientmodel.IType;

public class AnyMapper implements IMapper<AnySchema, IType> {

  private static final AnyMapper INSTANCE = new AnyMapper();

  private AnyMapper() {
    // private constructor
  }

  public static AnyMapper getInstance() {
    return INSTANCE;
  }

  @Override
  public IType map(AnySchema anySchema) {
    return ClassType.Object;
  }
}
