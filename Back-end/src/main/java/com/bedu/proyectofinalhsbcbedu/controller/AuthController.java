package com.bedu.proyectofinalhsbcbedu.controller;

import com.bedu.proyectofinalhsbcbedu.dto.UsuarioEntityDTO;
import com.bedu.proyectofinalhsbcbedu.security.auth.response.AuthResponse;
import com.bedu.proyectofinalhsbcbedu.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController{

    private final IAuthService authService;
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UsuarioEntityDTO regRequest){
        authService.registerUser(regRequest);
    return ResponseEntity.created(URI.create("0")).build();
    }

    // Se maneja el login por filtro JwtToHeaderFilter
}
