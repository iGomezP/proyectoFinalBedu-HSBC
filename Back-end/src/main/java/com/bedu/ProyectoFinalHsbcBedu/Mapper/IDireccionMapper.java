package com.bedu.ProyectoFinalHsbcBedu.Mapper;

import com.bedu.ProyectoFinalHsbcBedu.DTO.DireccionEntityDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.DireccionEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IDireccionMapper {
    DireccionEntityDTO toDTO(DireccionEntity data);

    DireccionEntity toEntity(DireccionEntityDTO data);
}
