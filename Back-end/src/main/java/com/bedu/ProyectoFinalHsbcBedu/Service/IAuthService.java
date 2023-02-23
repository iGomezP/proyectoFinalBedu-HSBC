package com.bedu.proyectofinalhsbcbedu.service;


import com.bedu.proyectofinalhsbcbedu.dto.UsuarioEntityDTO;

public interface IAuthService {

    void registerUser(UsuarioEntityDTO regRequest);

    // Se maneja el login por filtro JwtToHeaderFilter
}
