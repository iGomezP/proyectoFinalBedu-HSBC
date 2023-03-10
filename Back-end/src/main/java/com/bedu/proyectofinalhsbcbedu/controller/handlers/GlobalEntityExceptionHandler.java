package com.bedu.proyectofinalhsbcbedu.controller.handlers;

import com.bedu.proyectofinalhsbcbedu.entity.builders.ErrorResponse;
import com.bedu.proyectofinalhsbcbedu.exceptions.CustomProductException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
@Slf4j
public class GlobalEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final ErrorResponse errorResponse = new ErrorResponse();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new TreeMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            log.error(error.getDefaultMessage());
            errors.put(error.getField(), error.getDefaultMessage());
        }

        for(ObjectError error : ex.getBindingResult().getGlobalErrors()){
            log.error(error.getDefaultMessage());
            errors.put(error.getObjectName(), error.getDefaultMessage());
        }

        errorResponse.setErrores(errors);
        errorResponse.setStatusCode(ex.getStatusCode().value());
        errorResponse.setEndpoint(getEndpoint(request));

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
            errorResponse.setEndpoint(getEndpoint(request));
            log.error(ex.getLocalizedMessage());
        }

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(CustomProductException.class)
    public ResponseEntity<Object> handleDuplicateProduct(CustomProductException ex, WebRequest request){
        return handleExceptionInternal(
                ex,
                buildErrorResponse(ex, request),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Object> handleSignatureException(SignatureException ex, WebRequest request){
        return handleExceptionInternal(
                ex,
                buildErrorResponse(ex, request),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointer(NullPointerException ex, WebRequest request){
        return handleExceptionInternal(
                ex,
                buildErrorResponse(ex, request),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    // funciones auxiliares
    private String getEndpoint(WebRequest request){
        return request.getDescription(false).substring(4);
    }

    private ErrorResponse buildErrorResponse(Exception ex, WebRequest request){
        errorResponse.setTimestamp(now());
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setEndpoint(getEndpoint(request));
        log.error(ex.getMessage());
        return errorResponse;
    }
}
