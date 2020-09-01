package com.azure.autorest.azurefunctions.mapper;

public interface IMapper<FromT, ToT> {
    ToT map(FromT fromT);
}
