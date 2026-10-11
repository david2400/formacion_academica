package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response.ConsultarSolicitudFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response.ListarMisSolicitudesFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_response.ResponderFeedbackUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.ConfirmacionRespuestaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.ResponderFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.SolicitudFeedbackDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.SolicitudFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.EstadoSolicitudFeedback;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // TODO(permisos)
import org.springframework.web.bind.annotation.*;

/**
 * Endpoints "self" del estudiante. El estudiante SIEMPRE se toma del JWT de
 * gestion_alumnos, nunca de la URL: no hay forma de pedir la bandeja o de
 * responder a nombre de otro.
 */
@Tag(name = "Feedback · Mis feedbacks", description = "Bandeja y respuesta del estudiante autenticado")
@RestController
@RequestMapping("/control-academico/mis-feedbacks")
@RequiredArgsConstructor
public class MisFeedbacksController {

    private final ListarMisSolicitudesFeedbackUseCase listar;
    private final ConsultarSolicitudFeedbackUseCase consultar;
    private final ResponderFeedbackUseCase responder;
    private final FeedbackSeguridad seguridad;

    @Operation(summary = "Mi bandeja de feedbacks", description = "Disponibles primero; filtro opcional por estado derivado")
    @GetMapping
    // TODO(permisos): reactivar (el estudiante igual se toma de su token).
    // @PreAuthorize("@feedbackSeguridad.estudiante()")
    public ResponseEntity<Page<SolicitudFeedbackDto>> listar(@RequestParam(required = false) EstadoSolicitudFeedback estado,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(listar.listarMisSolicitudes(seguridad.estudianteActual(), estado,
                Paginacion.de(page, size, Sort.unsorted())));
    }

    @Operation(summary = "Formulario para responder", description = "404 si el feedback no aplica al estudiante")
    @GetMapping("/{aplicacionId}/clases/{claseId}")
    // TODO(permisos): reactivar (el estudiante igual se toma de su token).
    // @PreAuthorize("@feedbackSeguridad.estudiante()")
    public ResponseEntity<SolicitudFeedbackDetalleDto> consultar(@PathVariable Long aplicacionId, @PathVariable Long claseId) {
        return ResponseEntity.ok(consultar.consultarSolicitud(seguridad.estudianteActual(), aplicacionId, claseId));
    }

    @Operation(summary = "Enviar feedback")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Recibido"),
            @ApiResponse(responseCode = "400", description = "Respuestas inválidas; 'errores' trae el detalle por pregunta"),
            @ApiResponse(responseCode = "404", description = "El feedback no aplica al estudiante"),
            @ApiResponse(responseCode = "409", description = "Ya respondido, fuera de ventana o cerrado")
    })
    @PostMapping("/{aplicacionId}/clases/{claseId}/respuestas")
    // TODO(permisos): reactivar (el estudiante igual se toma de su token).
    // @PreAuthorize("@feedbackSeguridad.estudiante()")
    public ResponseEntity<ConfirmacionRespuestaFeedbackDto> responder(@PathVariable Long aplicacionId, @PathVariable Long claseId,
                                                                      @Valid @RequestBody ResponderFeedbackDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responder.responder(seguridad.estudianteActual(), aplicacionId, claseId, dto));
    }
}
