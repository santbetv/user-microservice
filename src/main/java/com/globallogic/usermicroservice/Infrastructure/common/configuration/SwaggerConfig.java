package com.globallogic.usermicroservice.Infrastructure.common.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API User Management")
                        .version("1.0")
                        .description("Microservicio para registro de usuarios y autenticación con JWT.")
                        .contact(new Contact()
                                .name("Equipo de Desarrollo")
                                .email("devs@empresa.com")
                        )
                ).addServersItem(new Server()
                        .url("https://microserviceuser-production.up.railway.app")
                        .description("Generated server url production"))
                .addServersItem(new Server()
                        .url("http://localhost:8080")
                        .description("Generated server url development"));
    }
}
