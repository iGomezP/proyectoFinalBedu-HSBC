package com.bedu.proyectofinalhsbcbedu.service;

import org.springframework.web.multipart.MultipartFile;

public interface IAwsImageService {


    String uploadImage(String keyName, MultipartFile imageFile);
}
