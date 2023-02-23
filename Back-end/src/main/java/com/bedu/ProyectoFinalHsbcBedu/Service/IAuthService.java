package com.bedu.ProyectoFinalHsbcBedu.Service;


import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioDTO;
import com.bedu.ProyectoFinalHsbcBedu.Security.Auth.Request.AuthRequest;
import com.bedu.ProyectoFinalHsbcBedu.Security.Auth.Response.AuthResponse;

public interface IAuthService {

    AuthResponse registerUser(UsuarioDTO regRequest);

    // Se maneja el login por filtro JwtToHeaderFilter
    //AuthResponse loginUser(AuthRequest authRequest);
}
