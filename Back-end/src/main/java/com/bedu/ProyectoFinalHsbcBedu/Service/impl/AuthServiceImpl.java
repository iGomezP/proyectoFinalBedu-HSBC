package com.bedu.proyectofinalhsbcbedu.service.impl;

import com.bedu.proyectofinalhsbcbedu.dto.UsuarioEntityDTO;
import com.bedu.proyectofinalhsbcbedu.entity.ERole;
import com.bedu.proyectofinalhsbcbedu.entity.UsuarioEntity;
import com.bedu.proyectofinalhsbcbedu.mapper.IUsuarioMapper;
import com.bedu.proyectofinalhsbcbedu.repository.IUsuarioRepository;
import com.bedu.proyectofinalhsbcbedu.security.JwtUtils;
import com.bedu.proyectofinalhsbcbedu.security.auth.response.AuthResponse;
import com.bedu.proyectofinalhsbcbedu.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void registerUser(UsuarioEntityDTO regRequest) {

        UsuarioEntityDTO usuarioDTO = UsuarioEntityDTO.builder()
                .name(regRequest.getName())
                .email(regRequest.getEmail())
                .userPassword(passwordEncoder.encode(regRequest.getUserPassword()))
                .direccion(regRequest.getDireccion())
                .rol(ERole.USER)
                .build();

        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(usuarioEntity);

        var jwtToken = JwtUtils.generateToken(usuarioEntity);

        log.info("Usuario creado exitosamente");

        AuthResponse.builder()
                .token(jwtToken)
                .build();

    }

    // Se maneja el login por filtro JwtToHeaderFilter
}
