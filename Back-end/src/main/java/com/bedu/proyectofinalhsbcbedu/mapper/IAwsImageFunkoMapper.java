package com.bedu.proyectofinalhsbcbedu.mapper;

import com.bedu.proyectofinalhsbcbedu.dto.AwsImageFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.entity.AwsImageFunkoEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IAwsImageFunkoMapper {
    AwsImageFunkoDTO toDTO(AwsImageFunkoEntity data);

    AwsImageFunkoEntity toEntity(AwsImageFunkoDTO data);
}
