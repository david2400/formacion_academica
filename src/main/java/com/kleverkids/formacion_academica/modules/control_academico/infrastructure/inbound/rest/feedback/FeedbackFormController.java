package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // TODO(permisos)
import org.springframework.web.bind.annotation.*;

/**
 * Autoría de formularios de feedback (coordinación/administración). Los
 * docentes pueden consultarlos (para entender sus resultados), no editarlos.
 * Toda escritura exige formulario en BORRADOR; publicado = inmutable.
 */
@Tag(name = "Feedback · Formularios", description = "Formularios configurables para evaluar clases: secciones, preguntas y ciclo de vida")
@RestController
@RequestMapping("/control-academico/feedback-forms")
@RequiredArgsConstructor
public class FeedbackFormController {

    private final CrearFeedbackFormUseCase crear;
    private final ActualizarFeedbackFormUseCase actualizar;
    private final ConsultarFeedbackFormUseCase consultar;
    private final ListarFeedbackFormsUseCase listar;
    private final DuplicarFeedbackFormUseCase duplicar;
    private final CambiarEstadoFeedbackFormUseCase cambiarEstado;
    private final AgregarSeccionFeedbackUseCase agregarSeccion;
    private final ActualizarSeccionFeedbackUseCase actualizarSeccion;
    private final EliminarSeccionFeedbackUseCase eliminarSeccion;
    private final ReordenarSeccionesFeedbackUseCase reordenarSecciones;
    private final AgregarPreguntaFeedbackUseCase agregarPregunta;
    private final ActualizarPreguntaFeedbackUseCase actualizarPregunta;
    private final EliminarPreguntaFeedbackUseCase eliminarPregunta;
    private final ReordenarPreguntasFeedbackUseCase reordenarPreguntas;

    @Operation(summary = "Listar formularios", description = "Paginado; filtra por texto del título y estado del motor")
    @GetMapping
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracionODocente()")
    public ResponseEntity<Page<FeedbackForm>> listar(@RequestParam(required = false) String texto,
                                                     @RequestParam(required = false) Long estadoId,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(listar.listar(new FiltroFeedbackFormDto(texto, estadoId), Paginacion.recientes(page, size)));
    }

    @Operation(summary = "Detalle del formulario", description = "Formulario con secciones, preguntas, opciones y niveles de escala")
    @GetMapping("/{formId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracionODocente()")
    public ResponseEntity<FeedbackFormDetalleDto> consultar(@PathVariable Long formId) {
        return ResponseEntity.ok(consultar.consultar(formId));
    }

    @Operation(summary = "Crear formulario", description = "Nace en BORRADOR, versión 1")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Formulario creado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackForm> crear(@Valid @RequestBody CrearFeedbackFormDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crear.crear(dto));
    }

    @Operation(summary = "Editar formulario", description = "Solo en BORRADOR (409 si está publicado)")
    @PutMapping("/{formId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackForm> actualizar(@PathVariable Long formId, @Valid @RequestBody ActualizarFeedbackFormDto dto) {
        return ResponseEntity.ok(actualizar.actualizar(formId, dto));
    }

    @Operation(summary = "Duplicar como nueva versión", description = "Copia completa en BORRADOR; la original no cambia")
    @PostMapping("/{formId}/duplicar")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackForm> duplicar(@PathVariable Long formId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(duplicar.duplicar(formId));
    }

    @Operation(summary = "Cambiar estado", description = """
            Transición en el motor FEEDBACK_FORM_LIFECYCLE (BORRADOR → PUBLICADO → ARCHIVADO).
            Publicar valida que el formulario esté completo. Los estados disponibles salen de
            GET /estados (máquina FEEDBACK_FORM_LIFECYCLE).
            """)
    @PatchMapping("/{formId}/estado")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackForm> cambiarEstado(@PathVariable Long formId, @Valid @RequestBody CambiarEstadoFeedbackDto dto) {
        return ResponseEntity.ok(cambiarEstado.cambiarEstado(formId, dto));
    }

    // ─── Secciones ───────────────────────────────────────────────────────────

    @Operation(summary = "Agregar sección")
    @PostMapping("/{formId}/secciones")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackSection> agregarSeccion(@PathVariable Long formId, @Valid @RequestBody GuardarSeccionFeedbackDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agregarSeccion.agregarSeccion(formId, dto));
    }

    @Operation(summary = "Editar sección")
    @PutMapping("/{formId}/secciones/{seccionId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackSection> actualizarSeccion(@PathVariable Long formId, @PathVariable Long seccionId,
                                                             @Valid @RequestBody GuardarSeccionFeedbackDto dto) {
        return ResponseEntity.ok(actualizarSeccion.actualizarSeccion(formId, seccionId, dto));
    }

    @Operation(summary = "Eliminar sección", description = "Elimina también sus preguntas")
    @DeleteMapping("/{formId}/secciones/{seccionId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<Void> eliminarSeccion(@PathVariable Long formId, @PathVariable Long seccionId) {
        eliminarSeccion.eliminarSeccion(formId, seccionId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reordenar secciones", description = "Recibe el orden completo de ids")
    @PutMapping("/{formId}/secciones/orden")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<Void> reordenarSecciones(@PathVariable Long formId, @Valid @RequestBody ReordenarFeedbackDto dto) {
        reordenarSecciones.reordenarSecciones(formId, dto);
        return ResponseEntity.noContent().build();
    }

    // ─── Preguntas ───────────────────────────────────────────────────────────

    @Operation(summary = "Agregar pregunta", description = "La configuración se valida según el tipo")
    @PostMapping("/{formId}/preguntas")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackFormDetalleDto.PreguntaDetalle> agregarPregunta(@PathVariable Long formId,
                                                                                 @Valid @RequestBody GuardarPreguntaFeedbackDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agregarPregunta.agregarPregunta(formId, dto));
    }

    @Operation(summary = "Editar pregunta", description = "Reemplaza sus opciones; puede moverla de sección")
    @PutMapping("/{formId}/preguntas/{preguntaId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<FeedbackFormDetalleDto.PreguntaDetalle> actualizarPregunta(@PathVariable Long formId, @PathVariable Long preguntaId,
                                                                                    @Valid @RequestBody GuardarPreguntaFeedbackDto dto) {
        return ResponseEntity.ok(actualizarPregunta.actualizarPregunta(formId, preguntaId, dto));
    }

    @Operation(summary = "Eliminar pregunta")
    @DeleteMapping("/{formId}/preguntas/{preguntaId}")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Long formId, @PathVariable Long preguntaId) {
        eliminarPregunta.eliminarPregunta(formId, preguntaId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reordenar preguntas de una sección", description = "Recibe el orden completo de ids")
    @PutMapping("/{formId}/secciones/{seccionId}/preguntas/orden")
    // TODO(permisos): reactivar cuando se usen roles de access_control.
    // @PreAuthorize("@feedbackSeguridad.administracion()")
    public ResponseEntity<Void> reordenarPreguntas(@PathVariable Long formId, @PathVariable Long seccionId,
                                                   @Valid @RequestBody ReordenarFeedbackDto dto) {
        reordenarPreguntas.reordenarPreguntas(formId, seccionId, dto);
        return ResponseEntity.noContent().build();
    }
}
