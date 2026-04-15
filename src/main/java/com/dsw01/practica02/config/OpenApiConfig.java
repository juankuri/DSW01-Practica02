package com.dsw01.practica02.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI appOpenApi() {
        return new OpenAPI().info(new Info()
                .title("API CRUD Empleados y Departamentos")
                .version("1.1.0")
                .description(
                        "API con autenticación HTTP Basic, rutas versionadas /api/v1 y soporte de listados paginados con page/size."));
    }
}
