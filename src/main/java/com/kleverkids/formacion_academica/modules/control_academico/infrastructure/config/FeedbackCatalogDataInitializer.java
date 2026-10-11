package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.config;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog.CrearCriterioFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog.CrearEscalaFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog.ListarCriteriosFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog.ListarEscalasFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarCriterioFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarEscalaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.NivelEscalaFeedbackDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Siembra el catálogo inicial de feedback SOLO si está vacío (mismo patrón que
 * ActivityTypeDefinitionDataInitializer: sin Flyway, la siembra va así). Es
 * un punto de partida, no configuración fija: todo se puede editar después
 * desde la administración.
 */
@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class FeedbackCatalogDataInitializer implements CommandLineRunner {

    private final ListarEscalasFeedbackUseCase listarEscalas;
    private final CrearEscalaFeedbackUseCase crearEscala;
    private final ListarCriteriosFeedbackUseCase listarCriterios;
    private final CrearCriterioFeedbackUseCase crearCriterio;

    @Override
    public void run(String... args) {
        try {
            if (listarEscalas.listarEscalas().isEmpty()) {
                crearEscala.crearEscala(GuardarEscalaFeedbackDto.builder()
                        .nombre("Calidad (1 a 5)")
                        .descripcion("Escala Likert de 1 (Muy deficiente) a 5 (Excelente)")
                        .activa(true)
                        .niveles(List.of(
                                nivel(1, "Muy deficiente"),
                                nivel(2, "Deficiente"),
                                nivel(3, "Regular"),
                                nivel(4, "Bueno"),
                                nivel(5, "Excelente")))
                        .build());
                log.info("✅ Escala de feedback inicial creada");
            }
            if (listarCriterios.listarCriterios().isEmpty()) {
                criterios().forEach(crearCriterio::crearCriterio);
                log.info("✅ Criterios de feedback iniciales creados");
            }
        } catch (Exception e) {
            log.warn("No se pudo inicializar el catálogo de feedback: {}", e.getMessage());
        }
    }

    private static NivelEscalaFeedbackDto nivel(int valor, String etiqueta) {
        return NivelEscalaFeedbackDto.builder().valor(BigDecimal.valueOf(valor)).etiqueta(etiqueta).build();
    }

    private static List<GuardarCriterioFeedbackDto> criterios() {
        return List.of(
                criterio("CALIDAD_CLASE", "Calidad de la clase"),
                criterio("CLARIDAD", "Claridad de la explicación"),
                criterio("DOMINIO_TEMA", "Dominio del tema"),
                criterio("METODOLOGIA", "Metodología"),
                criterio("MATERIAL", "Material utilizado"),
                criterio("PARTICIPACION", "Participación e interacción"),
                criterio("OBJETIVOS", "Cumplimiento de objetivos"),
                criterio("PERCEPCION_GENERAL", "Percepción general"));
    }

    private static GuardarCriterioFeedbackDto criterio(String codigo, String nombre) {
        return GuardarCriterioFeedbackDto.builder().codigo(codigo).nombre(nombre).activo(true).build();
    }
}
