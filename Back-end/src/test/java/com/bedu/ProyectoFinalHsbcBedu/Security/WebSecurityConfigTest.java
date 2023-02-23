package com.bedu.ProyectoFinalHsbcBedu.Security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WebSecurityConfigTest {

    @Test
    @DisplayName("Test CORS")
    void corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http:localhost:4200"));
        config.combine(null);
        assertEquals(Arrays.asList("http:localhost:4200"), config.getAllowedOrigins());
    }
}