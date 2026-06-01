package com.condosaas.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

@Bean
public OpenAPI personalizarOpeApi(){
    final String Esquemaseguridad = "BearerAuth";
    return new OpenAPI()
            .info(new Info()
                    .title("Api de Paking Backend")
                    .version("1.0.0")
                    .description("Documentacion de endpoints"))
            .addSecurityItem(new SecurityRequirement().addList(Esquemaseguridad))
            .components(new Components().addSecuritySchemes(Esquemaseguridad,
                    new SecurityScheme()
                            .name(Esquemaseguridad)
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("Bearer")
                            .bearerFormat("JWT")
                            .description("introduce tu token JWT en el texto")));



}



}
