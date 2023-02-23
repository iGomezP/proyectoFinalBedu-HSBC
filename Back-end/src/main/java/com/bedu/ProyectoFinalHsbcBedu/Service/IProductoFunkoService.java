package com.bedu.proyectofinalhsbcbedu.service;


import com.bedu.proyectofinalhsbcbedu.dto.ProductoFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.exceptions.CustomProductException;

import java.util.List;
import java.util.Optional;

public interface IProductoFunkoService {
    List<ProductoFunkoDTO> getAllFunkos();

    Optional<ProductoFunkoDTO> getFunkoById(Long id) throws CustomProductException;

    void createFunko(ProductoFunkoDTO funkoDTO) throws CustomProductException;

    void updateFunko(Long id, ProductoFunkoDTO funkoDTO) throws CustomProductException;

    void deleteFunko(Long id) throws CustomProductException;
}
