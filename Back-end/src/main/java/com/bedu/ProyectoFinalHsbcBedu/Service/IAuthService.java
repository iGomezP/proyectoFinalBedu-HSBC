package com.bedu.ProyectoFinalHsbcBedu.Service;


import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioEntityDTO;
import com.bedu.ProyectoFinalHsbcBedu.Security.Auth.Response.AuthResponse;

public interface IAuthService {

    AuthResponse registerUser(UsuarioEntityDTO regRequest);

    // Se maneja el login por filtro JwtToHeaderFilter
    //AuthResponse loginUser(AuthRequest authRequest);
}
