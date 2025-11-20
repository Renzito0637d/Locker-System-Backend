package com.lockersystem_backend.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()

                //Seguridad para habilitar el botón "Authorize" con JWT
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))

                // Definición del esquema Bearer Token (JWT)
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))

                //  Información de tu API (lo que ya tenías)
                .info(new Info()
                        .title("LockerSystem API")
                        .version("1.0")
                        .description("Documentación de API para el sistema de lockers")
                        .contact(new Contact()
                                .name("Juan")
                                .email("U22314908@utp.edu.pe")
                        )
                );
    }
}
