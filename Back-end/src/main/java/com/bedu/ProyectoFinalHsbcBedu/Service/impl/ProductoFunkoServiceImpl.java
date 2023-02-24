package com.bedu.proyectofinalhsbcbedu.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bedu.proyectofinalhsbcbedu.dto.AwsImageFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.dto.ProductoFunkoDTO;
import com.bedu.proyectofinalhsbcbedu.entity.ProductoFunkoEntity;
import com.bedu.proyectofinalhsbcbedu.exceptions.CustomProductException;
import com.bedu.proyectofinalhsbcbedu.mapper.IProductoFunkoMapper;
import com.bedu.proyectofinalhsbcbedu.repository.IProductoFunkoRepository;
import com.bedu.proyectofinalhsbcbedu.service.IProductoFunkoService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductoFunkoServiceImpl implements IProductoFunkoService {

    private final IProductoFunkoRepository funkoRepository;
    private final IProductoFunkoMapper funkoMapper;
    private final AmazonS3 amazonS3Client;

    @Value("${application.bucket.name}")
    private String bucketName;
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
    public void createFunko(String funkoJson, MultipartFile imageFunko) throws CustomProductException {
        // Convertir String a Json
        JsonObject newJson = convertToJson(funkoJson);

        // Subir imagen a Aws S3
        String fileName;
        try{
            fileName = getNewImageName(newJson, Objects.requireNonNull(imageFunko.getOriginalFilename()));
        } catch (NullPointerException ex){
            log.error("NullPointerException: " + ex.getMessage());
            throw new CustomProductException(ex.getMessage());
        }
        String s3Key = "imagesFunkos/" + fileName;
        log.info("Nuevo nombre de imagen: " + fileName);
        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageFunko.getSize());
            amazonS3Client.putObject(bucketName, s3Key, imageFunko.getInputStream(), metadata);
            log.info("Imagen subida: " + fileName);
        } catch (IOException ioe){
            log.error("IOException: " + ioe.getMessage());
            throw new CustomProductException(ioe.getMessage());
        } catch (AmazonS3Exception s3Exception){
            log.error("AmazonServiceException: " + s3Exception.getMessage());
            throw new CustomProductException(s3Exception.getMessage());
        } catch (AmazonClientException clientException){
            log.error("AmazonClientException Message: " + clientException.getMessage());
            throw new CustomProductException(clientException.getMessage());
        }

        // Recuperar url de la imagen
        String urlImage = amazonS3Client.getUrl(bucketName, s3Key).toString();

        // Construir ImagenDTO
        AwsImageFunkoDTO imagenDTO = buildImagenDTO(fileName, urlImage);

        // Construir funkoDTO
        ProductoFunkoDTO funkoDTO = ProductoFunkoDTO.builder()
                .name(newJson.get("name").toString())
                .price(newJson.get("price").getAsInt())
                .stock(newJson.get("stock").getAsInt())
                .layaway(newJson.get("layaway").getAsInt())
                .awsImageFunko(imagenDTO)
                .build();

        ProductoFunkoEntity funkoEntity = funkoMapper.toEntity(funkoDTO);
        if (funkoRepository.findOneByName(funkoEntity.getName())!=null){
            log.error(NO_FUNKO);
            throw new CustomProductException(NO_FUNKO);
        }
        log.info("Creando nuevo producto...");
        funkoMapper.toDTO(funkoRepository.save(funkoEntity));
        log.info("Producto creado exitosamente");
    }



    @Override
    public void updateFunko(Long id, ProductoFunkoDTO funkoDTO) throws CustomProductException {
        Optional<ProductoFunkoEntity> funkoExist = funkoRepository.findById(id);
        ProductoFunkoEntity funkoEntity = funkoMapper.toEntity(funkoDTO);
        if(funkoExist.isEmpty()){
            log.error(NO_FUNKO);
            throw new CustomProductException(NO_FUNKO);
        }
        if (funkoRepository.findOneByName(funkoEntity.getName())!=null && !funkoExist.get().getName().equals(funkoDTO.getName())){
            log.error("No se puede modificar el nombre del producto a uno ya existente");
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
            log.error(NO_FUNKO);
            throw new CustomProductException(NO_FUNKO);
        }
        try {
            funkoRepository.deleteById(id);
            log.info("Producto eliminado...");
        }catch (Exception e){
            log.error("Exception: "+e.getMessage());
            throw new CustomProductException(e.getMessage());
        }
    }

    // Funciones auxiliares
    public JsonObject convertToJson(String toJson){
        return new JsonParser().parse(toJson).getAsJsonObject();
    }

    private String getNewImageName(JsonObject funkoJson, String imageName) {
        String[] splitImageName = imageName.split("\\.");
        String funkoName = funkoJson.get("name").toString().replace(" ", "_").replace("\"", "").toLowerCase();
        String newImageName = splitImageName[0].replace(" ", "_").replace(".jpg", "").toLowerCase();
        return funkoName + "_" + newImageName + "_funko." + splitImageName[1];
    }

    private AwsImageFunkoDTO buildImagenDTO(String name, String url){
        return AwsImageFunkoDTO.builder()
                .name(name)
                .awsUrl(url)
                .build();
    }
}
