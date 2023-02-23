package com.bedu.ProyectoFinalHsbcBedu.Service.impl;

import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioEntityDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.ERole;
import com.bedu.ProyectoFinalHsbcBedu.Mapper.IUsuarioMapper;
import com.bedu.ProyectoFinalHsbcBedu.Repository.IUsuarioRepository;
import com.bedu.ProyectoFinalHsbcBedu.Security.Auth.Response.AuthResponse;
import com.bedu.ProyectoFinalHsbcBedu.Security.JwtUtils;
import com.bedu.ProyectoFinalHsbcBedu.Service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;


    @Override
    public AuthResponse registerUser(UsuarioEntityDTO regRequest) {

        UsuarioEntityDTO usuarioDTO = UsuarioEntityDTO.builder()
                .name(regRequest.getName())
                .email(regRequest.getEmail())
                .userPassword(passwordEncoder.encode(regRequest.getUserPassword()))
                .direccion(regRequest.getDireccion())
                .rol(ERole.USER)
                .build();

        com.bedu.ProyectoFinalHsbcBedu.Entity.UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(usuarioEntity);

        var jwtToken = JwtUtils.generateToken(usuarioEntity);

        log.info("Usuario creado exitosamente");

        return AuthResponse.builder()
                .token(jwtToken)
                .build();

    }

    // Se maneja el login por filtro JwtToHeaderFilter
    /*@Override
    public AuthResponse loginUser(AuthRequest authRequest) {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
            // Once authenticated send back token
            UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(authRequest.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("El usuario o la contraseña son incorrectos"));
            var jwtToken = JwtUtils.generateToken(usuarioEntity);

            return AuthResponse.builder()
                    .token(jwtToken)
                    .build();
    }*/
}
