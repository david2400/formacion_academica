package com.kleverkids.formacion_academica.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@OpenAPIDefinition(
        info = @Info(
                title = "Essenza Tienda API",
                version = "v1"
        ),
        servers = {
                @Server(url = "/", description = "Default Server")
        }
)
@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi controlAcademicoApi() {
        return GroupedOpenApi.builder()
                .group("control-academico")
                .displayName("Control Academico")
                .packagesToScan("com.kleverkids.formacion_academica.modules.control_academico")
                .pathsToMatch(
                        "/api/kleverkids/control-academico/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi estructuraInstitucionApi() {
        return GroupedOpenApi.builder()
                .group("estructura-institucion")
                .displayName("Estructura Institucion")
                .packagesToScan("com.kleverkids.formacion_academica.modules.estructura_institucion")
                .pathsToMatch("/api/kleverkids/estructura-institucion/**")
                .build();
    }

    @Bean
    public GroupedOpenApi gestionAlumnosApi() {
        return GroupedOpenApi.builder()
                .group("gestion-alumnos")
                .displayName("Estructura Institucion")
                .packagesToScan("com.kleverkids.formacion_academica.modules.gestion_alumnos")
                .pathsToMatch("/api/kleverkids/gestion-alumnos/**")
                .build();
    }

    @Bean
    public GroupedOpenApi admisionesApi() {
        return GroupedOpenApi.builder()
                .group("admisiones")
                .displayName("Admisiones")
                .packagesToScan("com.kleverkids.formacion_academica.modules.admisiones")
                .pathsToMatch("/api/kleverkids/admisiones/**")
                .build();
    }

    @Controller
    static class SwaggerUiRedirectController {

        @GetMapping(value = {"/", ""})
        public String redirectRoot() {
            return "redirect:/swagger-ui/index.html";
        }

        @GetMapping(value = {"/swagger-ui", "/swagger-ui/"})
        public String redirectSwaggerUi() {
            return "redirect:/swagger-ui/index.html";
        }
    }
}
