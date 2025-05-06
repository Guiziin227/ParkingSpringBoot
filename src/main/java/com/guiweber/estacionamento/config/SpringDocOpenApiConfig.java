package com.guiweber.estacionamento.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApiConfig {

    @Bean
    public OpenAPI springDocOpenApi() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("SecurityScheme", securityScheme()))
                .info(new Info()
                        .title("Rest API Parking")
                        .description("API para gerenciamento de estacionamento")
                        .version("v1.0.0")
                        .contact(new Contact().name("Guilherme Weber").email("gh63388@gmail.com"))
                );
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .description("Insira o token JWT no formato 'Bearer ")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");
    }
}
