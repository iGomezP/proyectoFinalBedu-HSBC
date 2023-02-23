package com.bedu.ProyectoFinalHsbcBedu.Mapper;

import com.bedu.ProyectoFinalHsbcBedu.DTO.ProductoFunkoDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.ProductoFunkoEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IProductoFunkoMapper {
    ProductoFunkoDTO toDTO(ProductoFunkoEntity productoFunkoEntity);

    ProductoFunkoEntity toEntity(ProductoFunkoDTO productoFunkoDTO);
}
