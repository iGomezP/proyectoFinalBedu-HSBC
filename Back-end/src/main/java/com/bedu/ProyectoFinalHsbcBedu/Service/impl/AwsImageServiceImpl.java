package com.bedu.proyectofinalhsbcbedu.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bedu.proyectofinalhsbcbedu.service.IAwsImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
//@ConfigurationProperties(prefix = "application.bucket.name")
public class AwsImageServiceImpl implements IAwsImageService {


    private final AmazonS3 amazonS3Client;

    @Value("${application.bucket.name}")
    private String bucketName;

    /*// Save to DB
                AwsImageFunkoEntity fileData = fileDataRepository.save(AwsImageFunkoEntity
                        .builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .build()
                );*/


    @Override
    public String uploadImage(String keyName, MultipartFile imageFile) {
        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageFile.getSize());
            amazonS3Client.putObject(bucketName, "imagesFunkos/" + keyName, imageFile.getInputStream(), metadata);
            return "File uploaded: " + keyName;
        } catch (IOException ioe){
            log.error("IOException: " + ioe.getMessage());
        } catch (AmazonS3Exception s3Exception){
            log.error("AmazonServiceException: " + s3Exception.getMessage());
            throw s3Exception;
        } catch (AmazonClientException clientException){
            log.error("AmazonClientException Message: " + clientException.getMessage());
            throw clientException;
        }
        return "File not uploaded: " + keyName;
    }
}
