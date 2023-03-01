package com.bedu.proyectofinalhsbcbedu.mapper;

import com.bedu.proyectofinalhsbcbedu.dto.ProductoFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.entity.ProductoFunkoEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface IProductoFunkoMapper {
    ProductoFunkoDTO toDTO(ProductoFunkoEntity productoFunkoEntity);

    ProductoFunkoEntity toEntity(ProductoFunkoDTO productoFunkoDTO);
}
