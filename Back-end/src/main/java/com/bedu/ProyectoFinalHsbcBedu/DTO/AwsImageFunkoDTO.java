package com.bedu.proyectofinalhsbcbedu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AwsImageFunkoDTO {
    private Long id;
    private String name;
    private String awsUrl;
}
