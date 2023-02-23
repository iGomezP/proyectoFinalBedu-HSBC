package com.bedu.ProyectoFinalHsbcBedu.Mapper;

import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioEntityDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.UsuarioEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IUsuarioMapper {

    UsuarioEntityDTO toDTO(UsuarioEntity usuarioEntity);


    UsuarioEntity toEntity(UsuarioEntityDTO usuarioEntityDTO);
}
