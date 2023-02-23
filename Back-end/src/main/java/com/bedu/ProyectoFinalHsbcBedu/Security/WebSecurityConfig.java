package com.bedu.ProyectoFinalHsbcBedu.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    //private final AuthenticationProvider authenticacionProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationManager authManager
    )throws Exception {

        JwtToHeaderFilter jwtToHeaderFilter = new JwtToHeaderFilter();
        jwtToHeaderFilter.setAuthenticationManager(authManager);
        jwtToHeaderFilter.setFilterProcessesUrl("/api/auth/login");

        return http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                // Endpoint login
                .requestMatchers(HttpMethod.DELETE,"/api/auth/login")
                .hasAuthority("ADMIN")
                // Endpoint usuarios
                .requestMatchers(HttpMethod.DELETE,"/api/usuarios/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/api/usuarios/**")
                .hasAnyAuthority("USER", "ADMIN")
                // Endpoint productos
                .requestMatchers(HttpMethod.GET, "/api/productos/**")
                .hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/api/productos/**")
                .hasAuthority("ADMIN")
                // Entry Endpoint: register
                .requestMatchers(HttpMethod.POST, "/api/auth/register")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login")
                .permitAll()
                // Rest of endpoints
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtToHeaderFilter)
                //.authenticationProvider(authenticacionProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Requestor-Type","Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("X-Get-Header", "Authorization"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
