package com.bedu.ProyectoFinalHsbcBedu.Service.impl;

import com.bedu.ProyectoFinalHsbcBedu.DTO.ProductoFunkoDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.ProductoFunkoEntity;
import com.bedu.ProyectoFinalHsbcBedu.Exceptions.CustomProductException;
import com.bedu.ProyectoFinalHsbcBedu.Mapper.IProductoFunkoMapper;
import com.bedu.ProyectoFinalHsbcBedu.Repository.IProductoFunkoRepository;
import com.bedu.ProyectoFinalHsbcBedu.Service.IProductoFunkoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductoFunkoServiceImpl implements IProductoFunkoService {

    private final IProductoFunkoRepository funkoRepository;
    private final IProductoFunkoMapper funkoMapper;
    @Override
    public List<ProductoFunkoDTO> gellAllFunkos() {
        List<ProductoFunkoEntity> funkos = funkoRepository.findAll();
        return funkos.stream().map(funkoMapper::toDTO).toList();
    }

    @Override
    public Optional<ProductoFunkoDTO> getFunkoById(Long id) throws Exception{
        Optional<ProductoFunkoEntity> funkoExist = funkoRepository.findById(id);
        if (funkoExist.isEmpty()) {
            throw new CustomProductException("El producto no existe");
        }
        return Optional.ofNullable(funkoMapper.toDTO(funkoExist.get()));
    }

    @Override
    public ProductoFunkoDTO createFunko(ProductoFunkoDTO funkoDTO) throws Exception {
        ProductoFunkoEntity funkoEntity = funkoMapper.toEntity(funkoDTO);
        if (funkoRepository.findOneByName(funkoEntity.getName())!=null){
            throw new CustomProductException("El producto ya existe");
        }
        log.info("Creando nuevo producto...");
        return funkoMapper.toDTO(funkoRepository.save(funkoEntity));
    }

    @Override
    public ProductoFunkoDTO updateFunko(Long id, ProductoFunkoDTO funkoDTO) throws Exception {
        Optional<ProductoFunkoEntity> funkoExist = funkoRepository.findById(id);
        ProductoFunkoEntity funkoEntity = funkoMapper.toEntity(funkoDTO);
        if(funkoExist.isEmpty()){
            throw new CustomProductException("El producto no existe");
        }
        if (funkoRepository.findOneByName(funkoEntity.getName())!=null && !funkoExist.get().getName().equals(funkoDTO.getName())){
            throw new CustomProductException("No se puede modificar el nombre del producto a uno ya existente");
        }
        ProductoFunkoEntity funkoEntityNew = funkoExist.get();
        funkoEntityNew.setName(funkoDTO.getName());
        funkoEntityNew.setPrice(funkoDTO.getPrice());
        funkoEntityNew.setLayaway(funkoDTO.getLayaway());
        funkoEntityNew.setStock(funkoDTO.getStock());
        log.info("Producto actualizado...");
        return funkoMapper.toDTO(funkoRepository.save(funkoEntityNew));
    }

    @Override
    public void deleteFunko(Long id) throws Exception {
        Optional<ProductoFunkoEntity> funkoExist = funkoRepository.findById(id);
        if(funkoExist.isEmpty()){
            throw new CustomProductException("El producto no existe");
        }
        try {
            funkoRepository.deleteById(id);
            log.info("Producto eliminado...");
        }catch (Exception e){
            throw  new CustomProductException(e.getMessage());
        }
    }
}
