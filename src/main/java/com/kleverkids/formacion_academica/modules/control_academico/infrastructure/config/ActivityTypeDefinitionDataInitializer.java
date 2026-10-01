package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.config;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.ActivityTypeDefinitionService;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.CrearActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.valueobject.CategoriaTipoActividad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Siembra el catálogo inicial de tipos de actividad (Etapa 1: QUESTION,
 * QUESTION_GROUP, CONTENT — ver roadmap aprobado). Replica el patrón de
 * {@code NivelesEducativosDataInitializer}: no hay Flyway en este proyecto
 * (ddl-auto=update gobierna el esquema), así que la siembra de catálogos se
 * hace así. Agregar un tipo nuevo más adelante (VIDEO, FLASHCARDS, juegos...)
 * es una fila nueva aquí + un ActivityHandler, no un cambio a esta clase.
 */
@Component
@Order(2)
@RequiredArgsConstructor
@Slf4j
public class ActivityTypeDefinitionDataInitializer implements CommandLineRunner {

    private final ActivityTypeDefinitionService activityTypeDefinitionService;

    @Override
    public void run(String... args) {
        for (CrearActivityTypeDefinitionDto tipo : tiposEtapa1()) {
            try {
                if (activityTypeDefinitionService.consultarPorTipo(tipo.getType()).isEmpty()) {
                    activityTypeDefinitionService.crear(tipo);
                    log.info("✅ Tipo de actividad inicializado: {}", tipo.getType());
                }
            } catch (Exception e) {
                log.warn("No se pudo inicializar el tipo de actividad {}: {}", tipo.getType(), e.getMessage());
            }
        }
    }

    private List<CrearActivityTypeDefinitionDto> tiposEtapa1() {
        return List.of(
                CrearActivityTypeDefinitionDto.builder()
                        .type("QUESTION")
                        .nombre("Pregunta")
                        .descripcion("Actividad de una sola pregunta tomada del banco de preguntas")
                        .icono("help-circle")
                        .categoria(CategoriaTipoActividad.EVALUACION)
                        .activo(true)
                        .build(),
                CrearActivityTypeDefinitionDto.builder()
                        .type("QUESTION_GROUP")
                        .nombre("Grupo de preguntas")
                        .descripcion("Actividad compuesta por varias preguntas del banco de preguntas")
                        .icono("list-checks")
                        .categoria(CategoriaTipoActividad.EVALUACION)
                        .activo(true)
                        .build(),
                CrearActivityTypeDefinitionDto.builder()
                        .type("CONTENT")
                        .nombre("Contenido")
                        .descripcion("Actividad de lectura o contenido enriquecido")
                        .icono("file-text")
                        .categoria(CategoriaTipoActividad.CONTENIDO)
                        .activo(true)
                        .build()
        );
    }
}
