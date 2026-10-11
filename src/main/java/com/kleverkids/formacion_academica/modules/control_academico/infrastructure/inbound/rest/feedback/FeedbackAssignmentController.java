package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_assignment.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.ActualizarFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.CrearFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.FiltroFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.CambiarEstadoFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // TODO(permisos)
import org.springframework.web.bind.annotation.*;

/** Aplicación de formularios publicados a una clase o a las clases de un grupo. */
@Tag(name = "Feedback · Aplicaciones", description = "Aplicar un formulario a clases reales, con ventana y reglas de elegibilidad")
@RestController
@RequestMapping("/control-academico/feedback-aplicaciones")
@RequiredArgsConstructor
public class FeedbackAssignmentController {

    private final CrearFeedbackAssignmentUseCase crear;
    private final ActualizarFeedbackAssignmentUseCase actualizar;
    private final ConsultarFeedbackAssignmentUseCase consultar;
    private final ListarFeedbackAssignmentsUseCase listar;
    private final CambiarEstadoFeedbackAssignmentUseCase cambiarEstado;

    @Operation(summary = "Listar aplicaciones")
    @GetMapping
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<Page<FeedbackAssignment>> listar(@RequestParam(required = false) Long formId,
                                                           @RequestParam(required = false) Long claseId,
                                                           @RequestParam(required = false) Long grupoId,
                                                           @RequestParam(required = false) Long estadoId,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(listar.listar(new FiltroFeedbackAssignmentDto(formId, claseId, grupoId, estadoId),
                Paginacion.recientes(page, size)));
    }

    @Operation(summary = "Detalle de la aplicación")
    @GetMapping("/{aplicacionId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackAssignment> consultar(@PathVariable Long aplicacionId) {
        return ResponseEntity.ok(consultar.consultar(aplicacionId));
    }

    @Operation(summary = "Crear aplicación", description = "Formulario PUBLICADO + exactamente uno de clase_id / grupo_id (con desde/hasta)")
    @PostMapping
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackAssignment> crear(@Valid @RequestBody CrearFeedbackAssignmentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crear.crear(dto));
    }

    @Operation(summary = "Editar aplicación", description = "Ventana y nombre; reglas solo en BORRADOR")
    @PutMapping("/{aplicacionId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackAssignment> actualizar(@PathVariable Long aplicacionId,
                                                         @Valid @RequestBody ActualizarFeedbackAssignmentDto dto) {
        return ResponseEntity.ok(actualizar.actualizar(aplicacionId, dto));
    }

    @Operation(summary = "Cambiar estado", description = "Motor FEEDBACK_APLICACION_LIFECYCLE: activar, cerrar o cancelar (con motivo)")
    @PatchMapping("/{aplicacionId}/estado")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackAssignment> cambiarEstado(@PathVariable Long aplicacionId,
                                                            @Valid @RequestBody CambiarEstadoFeedbackDto dto) {
        return ResponseEntity.ok(cambiarEstado.cambiarEstado(aplicacionId, dto));
    }
}
