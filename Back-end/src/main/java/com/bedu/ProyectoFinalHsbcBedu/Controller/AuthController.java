package com.bedu.ProyectoFinalHsbcBedu.Controller;

import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioDTO;
import com.bedu.ProyectoFinalHsbcBedu.Security.Auth.Response.AuthResponse;
import com.bedu.ProyectoFinalHsbcBedu.Service.IAuthService;
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
    public ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody UsuarioDTO regRequest){
        authService.registerUser(regRequest);
    return ResponseEntity.created(URI.create("/login")).build();
    }

    // Se maneja el login por filtro JwtToHeaderFilter
    /*@PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody AuthRequest authRequest){
    return ResponseEntity.ok(authService.loginUser(authRequest));
    }*/
}
