package com.bedu.ProyectoFinalHsbcBedu.Service.impl;

import com.bedu.ProyectoFinalHsbcBedu.DTO.DireccionDTO;
import com.bedu.ProyectoFinalHsbcBedu.DTO.UsuarioDTO;
import com.bedu.ProyectoFinalHsbcBedu.Entity.ERole;
import com.bedu.ProyectoFinalHsbcBedu.Entity.UsuarioEntity;
import com.bedu.ProyectoFinalHsbcBedu.Mapper.IUsuarioMapper;
import com.bedu.ProyectoFinalHsbcBedu.Repository.IUsuarioRepository;
import com.bedu.ProyectoFinalHsbcBedu.Security.Auth.Request.AuthRequest;
import com.bedu.ProyectoFinalHsbcBedu.Security.Auth.Response.AuthResponse;
import com.bedu.ProyectoFinalHsbcBedu.Security.JwtUtils;
import com.bedu.ProyectoFinalHsbcBedu.Service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final IUsuarioRepository usuarioRepository;
    private final IUsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;


    @Override
    public AuthResponse registerUser(UsuarioDTO regRequest) {

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .name(regRequest.getName())
                .email(regRequest.getEmail())
                .userPassword(passwordEncoder.encode(regRequest.getUserPassword()))
                .direccion(regRequest.getDireccion())
                .rol(ERole.USER)
                .build();

        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuarioDTO);
        usuarioRepository.save(usuarioEntity);

        var jwtToken = jwtUtils.generateToken(usuarioEntity);

        log.info("Usuario creado exitosamente");

        return AuthResponse.builder()
                .token(jwtToken)
                .build();

    }

    @Override
    public AuthResponse loginUser(AuthRequest authRequest) {

        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(),
                            authRequest.getPassword()
                    )
            );
        } catch (Exception e){
            System.out.println(e);
        }

        // Once authenticated send back token
        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("El usuario o la contraseña son incorrectos"));
        var jwtToken = jwtUtils.generateToken(usuarioEntity);

        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
