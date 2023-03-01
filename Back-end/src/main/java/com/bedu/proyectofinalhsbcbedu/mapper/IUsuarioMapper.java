package com.bedu.proyectofinalhsbcbedu.mapper;

import com.bedu.proyectofinalhsbcbedu.dto.UsuarioEntityDTO;
import com.bedu.proyectofinalhsbcbedu.entity.UsuarioEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IUsuarioMapper {

    UsuarioEntityDTO toDTO(UsuarioEntity usuarioEntity);


    UsuarioEntity toEntity(UsuarioEntityDTO usuarioEntityDTO);
}
