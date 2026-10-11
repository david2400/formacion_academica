package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackScaleDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarCriterioFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.GuardarEscalaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackCriterion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // TODO(permisos)
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Catálogos reutilizables de Feedback: escalas y criterios. */
@Tag(name = "Feedback · Catálogos", description = "Escalas reutilizables (Likert) y criterios de evaluación")
@RestController
@RequestMapping("/control-academico/feedback-catalogos")
@RequiredArgsConstructor
public class FeedbackCatalogController {

    private final ListarEscalasFeedbackUseCase listarEscalas;
    private final CrearEscalaFeedbackUseCase crearEscala;
    private final ActualizarEscalaFeedbackUseCase actualizarEscala;
    private final ListarCriteriosFeedbackUseCase listarCriterios;
    private final CrearCriterioFeedbackUseCase crearCriterio;
    private final ActualizarCriterioFeedbackUseCase actualizarCriterio;

    @Operation(summary = "Listar escalas con sus niveles")
    @GetMapping("/escalas")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracionODocente()")
    public ResponseEntity<List<FeedbackScaleDetalleDto>> listarEscalas() {
        return ResponseEntity.ok(listarEscalas.listarEscalas());
    }

    @Operation(summary = "Crear escala")
    @PostMapping("/escalas")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackScaleDetalleDto> crearEscala(@Valid @RequestBody GuardarEscalaFeedbackDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearEscala.crearEscala(dto));
    }

    @Operation(summary = "Editar escala", description = "Si ya la usa un formulario publicado, solo cambian nombre, descripción y estado")
    @PutMapping("/escalas/{escalaId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackScaleDetalleDto> actualizarEscala(@PathVariable Long escalaId,
                                                                   @Valid @RequestBody GuardarEscalaFeedbackDto dto) {
        return ResponseEntity.ok(actualizarEscala.actualizarEscala(escalaId, dto));
    }

    @Operation(summary = "Listar criterios")
    @GetMapping("/criterios")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracionODocente()")
    public ResponseEntity<List<FeedbackCriterion>> listarCriterios() {
        return ResponseEntity.ok(listarCriterios.listarCriterios());
    }

    @Operation(summary = "Crear criterio")
    @PostMapping("/criterios")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackCriterion> crearCriterio(@Valid @RequestBody GuardarCriterioFeedbackDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearCriterio.crearCriterio(dto));
    }

    @Operation(summary = "Editar criterio", description = "El código no cambia: es la clave de la analítica histórica")
    @PutMapping("/criterios/{criterioId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackCriterion> actualizarCriterio(@PathVariable Long criterioId,
                                                               @Valid @RequestBody GuardarCriterioFeedbackDto dto) {
        return ResponseEntity.ok(actualizarCriterio.actualizarCriterio(criterioId, dto));
    }
}
