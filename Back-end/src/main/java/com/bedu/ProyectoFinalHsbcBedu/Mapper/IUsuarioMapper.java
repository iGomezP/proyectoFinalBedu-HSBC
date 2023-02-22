package com.bedu.ProyectoFinalHsbcBedu.Mapper;

import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.UsuarioEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IUsuarioMapper {

    UsuarioDTO toDTO(UsuarioEntity usuarioEntity);


    UsuarioEntity toEntity(UsuarioDTO usuarioDTO);
}
