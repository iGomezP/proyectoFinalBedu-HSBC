package com.bedu.ProyectoFinalHsbcBedu.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
// This intercepts all HTTP Requests
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        // Extract header from request
        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;

        // Check if header exists
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            // don't continue with execution
            return;
        }

        // Extract token from header
        token = authHeader.substring(7);

        // Extract user email and compare signature
        try{
            userEmail = jwtUtils.extractUsername(token);
            if (userEmail!= null && SecurityContextHolder.getContext().getAuthentication() == null){
                // Get the user details from DB
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                // Check is valid
                if (jwtUtils.isTokenValid(token, userDetails)){
                    // Create authToken
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    // Send details from request
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    // Update Security context holder
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }catch (Exception ex){
            log.error(ex.getMessage());
        }


        // always pass to next step
        filterChain.doFilter(request, response);
    }
}
