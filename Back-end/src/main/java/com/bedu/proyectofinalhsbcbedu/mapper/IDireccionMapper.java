package com.bedu.proyectofinalhsbcbedu.mapper;

import com.bedu.proyectofinalhsbcbedu.dto.DireccionEntityDTO;
import com.bedu.proyectofinalhsbcbedu.entity.DireccionEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IDireccionMapper {
    DireccionEntityDTO toDTO(DireccionEntity data);

    DireccionEntity toEntity(DireccionEntityDTO data);
}
