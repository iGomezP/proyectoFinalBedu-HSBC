package com.bedu.ProyectoFinalHsbcBedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ProyectoFinalHsbcBeduApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoFinalHsbcBeduApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ProyectoFinalHsbcBeduApplication.class);
	}
}
