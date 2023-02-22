package com.bedu.ProyectoFinalHsbcBedu.Service;


import com.bedu.ProyectoFinalHsbcBedu.DTO.ProductoFunkoDTO;

import java.util.List;
import java.util.Optional;

public interface IProductoFunkoService {
    List<ProductoFunkoDTO> gellAllFunkos();

    Optional<ProductoFunkoDTO> getFunkoById(Long id) throws Exception;

    ProductoFunkoDTO createFunko(ProductoFunkoDTO funkoDTO) throws Exception;

    ProductoFunkoDTO updateFunko(Long id, ProductoFunkoDTO funkoDTO) throws Exception;

    void deleteFunko(Long id) throws Exception;
}
