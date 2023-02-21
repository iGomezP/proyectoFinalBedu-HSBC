package com.bedu.ProyectoFinalHsbcBedu.Entity.builders;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_EMPTY)
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int statusCode;
    private String statusCodeString;
    private HttpStatus status;
    private String reason;
    private String message;
    private String endpoint;
    private Object errors;
    private Map<String, String> errores;
}
