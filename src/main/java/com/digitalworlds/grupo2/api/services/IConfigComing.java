package com.digitalworlds.grupo2.api.services;

import com.digitalworlds.grupo2.api.dtos.DTOConfigComing;

public interface IConfigComing {

    DTOConfigComing getConfigComing(String region);

    DTOConfigComing postConfigComing(DTOConfigComing dtoConfigComing);

    DTOConfigComing putConfigComing(DTOConfigComing dtoConfigComing);

    DTOConfigComing deleteConfigComing(String region);

    IInfo getIInfo();

}
