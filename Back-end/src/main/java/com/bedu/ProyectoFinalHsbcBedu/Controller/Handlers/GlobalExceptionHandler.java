package com.bedu.ProyectoFinalHsbcBedu.Controller.Handlers;

import com.bedu.ProyectoFinalHsbcBedu.Entity.builders.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
import java.util.TreeMap;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorResponse errorResponse = new ErrorResponse();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new TreeMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }

        for(ObjectError error : ex.getBindingResult().getGlobalErrors()){
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }

        errorResponse.setErrores(errors);
        errorResponse.setStatusCode(ex.getStatusCode().value());
        errorResponse.setEndpoint(request.getDescription(false).substring(4));

        return handleExceptionInternal(ex, errorResponse, headers, HttpStatus.BAD_REQUEST,request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(status.value());
        log.error(ex.getMessage());

        return handleExceptionInternal(ex, errorResponse, headers, status,request);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSqlException(SQLException ex, WebRequest request){
        int sqlCode = ex.getErrorCode();
        if (sqlCode == 1062) {
            errorResponse.setTimestamp(now());
            errorResponse.setMessage(ex.getMessage().substring(0, 15));
            errorResponse.setEndpoint(request.getDescription(false).substring(4));
            log.error(ex.getLocalizedMessage());
        }
        /*if (ex.getLocalizedMessage().contains("Duplicate entry")){
            errorResponse.setTimestamp(now());
            errorResponse.setStatusCode(ex.getErrorCode());
            errorResponse.setMessage(ex.getMessage().substring(0,15));
            errorResponse.setEndpoint(request.getDescription(false).substring(4));
        }*/

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        //return ResponseEntity.badRequest().body(errorResponse);
    }
}
