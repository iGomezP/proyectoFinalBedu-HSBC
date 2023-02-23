package com.bedu.proyectofinalhsbcbedu.security;

import com.bedu.proyectofinalhsbcbedu.entity.UsuarioEntity;
import com.bedu.proyectofinalhsbcbedu.security.auth.request.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Slf4j
public class JwtToHeaderFilter extends UsernamePasswordAuthenticationFilter {
    private final Gson gson = new Gson();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthRequest authCredentials = new AuthRequest();

        try{
            authCredentials = new ObjectMapper()
                    .readValue(request.getReader(), AuthRequest.class);
        } catch (IOException ex){
            log.error(ex.getMessage().substring(0,26));
        }

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredentials.getUsername(),
                authCredentials.getPassword()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException{
        UsuarioEntity userDetails = (UsuarioEntity) authResult.getPrincipal();
        Map<String, String> responseJson = new HashMap<>();

        String token = JwtUtils.generateToken(userDetails);
        response.setHeader("Authorization", "Bearer " + token);

        /* Devolver el nombre de usuario como respuesta */
        responseJson.put("User", userDetails.getName());
        response.getWriter().write(this.gson.toJson(responseJson));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException{
        log.error(failed.getMessage());
        // Devolver 401 como respuesta
        response.setStatus(401);
        response.getWriter().flush();
    }
}
