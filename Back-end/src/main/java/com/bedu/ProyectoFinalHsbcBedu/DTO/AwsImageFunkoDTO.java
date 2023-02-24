package com.bedu.proyectofinalhsbcbedu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AwsImageFunkoDTO {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String name;
    private String awsUrl;
}
