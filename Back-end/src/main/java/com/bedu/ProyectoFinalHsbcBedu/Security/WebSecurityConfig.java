package com.bedu.proyectofinalhsbcbedu.security;

import com.bedu.proyectofinalhsbcbedu.entity.ERole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
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
    private static final String AUTH_LOGIN = "/api/auth/login";
    private static final String ROLE_ADMIN = ERole.ADMIN.toString();

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationManager authManager
    )throws Exception {

        JwtToHeaderFilter jwtToHeaderFilter = new JwtToHeaderFilter();
        jwtToHeaderFilter.setAuthenticationManager(authManager);
        jwtToHeaderFilter.setFilterProcessesUrl(AUTH_LOGIN);

        return http
                .csrf().disable()
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests()
                // Endpoint login
                .requestMatchers(HttpMethod.DELETE,AUTH_LOGIN)
                .hasAuthority(ROLE_ADMIN)
                // Endpoint usuarios
                .requestMatchers(HttpMethod.DELETE,"/api/usuarios/**")
                .hasAuthority(ROLE_ADMIN)
                .requestMatchers("/api/usuarios/**")
                .hasAnyAuthority("USER", ROLE_ADMIN)
                // Endpoint productos
                .requestMatchers(HttpMethod.GET, "/api/productos/**")
                .hasAnyAuthority("USER", ROLE_ADMIN)
                .requestMatchers("/api/productos/**")
                .hasAuthority(ROLE_ADMIN)
                // Entry Endpoint: register
                .requestMatchers(HttpMethod.POST, "/api/auth/register")
                .permitAll()
                .requestMatchers(HttpMethod.POST, AUTH_LOGIN)
                .permitAll()
                // Rest of endpoints
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(jwtToHeaderFilter)
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
