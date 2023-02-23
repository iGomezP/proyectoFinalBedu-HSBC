package com.bedu.proyectofinalhsbcbedu.service.impl;

import com.bedu.proyectofinalhsbcbedu.dto.ProductoFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.entity.ProductoFunkoEntity;
import com.bedu.proyectofinalhsbcbedu.exceptions.CustomProductException;
import com.bedu.proyectofinalhsbcbedu.mapper.IProductoFunkoMapper;
import com.bedu.proyectofinalhsbcbedu.repository.IProductoFunkoRepository;
import com.bedu.proyectofinalhsbcbedu.service.IProductoFunkoService;
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
    private static final String NO_FUNKO = "El producto no existe";

    @Override
    public List<ProductoFunkoDTO> getAllFunkos() {
        List<ProductoFunkoEntity> funkos = funkoRepository.findAll();
        return funkos.stream().map(funkoMapper::toDTO).toList();
    }

    @Override
    public Optional<ProductoFunkoDTO> getFunkoById(Long id) throws CustomProductException{
        Optional<ProductoFunkoEntity> funkoExist = funkoRepository.findById(id);
        if (funkoExist.isEmpty()) {
            throw new CustomProductException(NO_FUNKO);
        }
        return Optional.ofNullable(funkoMapper.toDTO(funkoExist.get()));
    }

    @Override
    public void createFunko(ProductoFunkoDTO funkoDTO) throws CustomProductException {
        ProductoFunkoEntity funkoEntity = funkoMapper.toEntity(funkoDTO);
        if (funkoRepository.findOneByName(funkoEntity.getName())!=null){
            throw new CustomProductException(NO_FUNKO);
        }
        log.info("Creando nuevo producto...");
        funkoMapper.toDTO(funkoRepository.save(funkoEntity));
    }

    @Override
    public void updateFunko(Long id, ProductoFunkoDTO funkoDTO) throws CustomProductException {
        Optional<ProductoFunkoEntity> funkoExist = funkoRepository.findById(id);
        ProductoFunkoEntity funkoEntity = funkoMapper.toEntity(funkoDTO);
        if(funkoExist.isEmpty()){
            throw new CustomProductException(NO_FUNKO);
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
        funkoMapper.toDTO(funkoRepository.save(funkoEntityNew));
    }

    @Override
    public void deleteFunko(Long id) throws CustomProductException {
        Optional<ProductoFunkoEntity> funkoExist = funkoRepository.findById(id);
        if(funkoExist.isEmpty()){
            throw new CustomProductException(NO_FUNKO);
        }
        try {
            funkoRepository.deleteById(id);
            log.info("Producto eliminado...");
        }catch (Exception e){
            throw  new CustomProductException(e.getMessage());
        }
    }
}
