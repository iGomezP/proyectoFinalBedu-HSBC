package com.bedu.ProyectoFinalHsbcBedu.Mapper;

import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.DireccionEntity;
import com.bedu.ProyectoFinalHsbcBedu.Entity.UsuarioEntity;
import com.bedu.ProyectoFinalHsbcBedu.Repository.IUsuarioRepository;
import org.mapstruct.*;
import org.springframework.data.crossstore.ChangeSetPersister;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IUsuarioMapper {

    UsuarioDTO toDTO(UsuarioEntity usuarioEntity);


    UsuarioEntity toEntity(UsuarioDTO usuarioDTO);

/*    @AfterMapping
    default void aftertoDTO(UsuarioEntity usuarioEntity, @MappingTarget UsuarioDTO usuarioDTO){
        usuarioDTO.setDireccion(usuarioEntity.getDireccion()==null?null:usuarioEntity.getDireccion().getId());
    }

    @AfterMapping
    default void aftertoEntity(UsuarioDTO usuarioDTO, @MappingTarget UsuarioEntity usuarioEntity, @Context IUsuarioRepository usuarioRepository){
        if (usuarioDTO.getDireccion() != null && (usuarioEntity.getDireccion() == null || !usuarioEntity.getDireccion().getId().equals(usuarioDTO.getDireccion()))){
            final DireccionEntity direccionEntity = usuarioRepository.findById(usuarioDTO.getId())
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
            usuarioEntity.setDireccion(direccionEntity);
        }
    }*/
}
