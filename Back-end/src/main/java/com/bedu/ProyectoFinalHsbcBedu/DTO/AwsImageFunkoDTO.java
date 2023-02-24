package com.bedu.proyectofinalhsbcbedu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AwsImageFunkoDTO {
    private Long id;
    private String name;
    private String awsUrl;
}
