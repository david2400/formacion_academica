package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ActualizarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.CancelarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ConsultarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.CrearLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.EliminarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ListarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.ActualizarLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.CrearLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona la asignación de secuencias de aprendizaje a grupos o estudiantes")
@Tag(name = "Asignaciones de secuencias", description = "Asignar una LearningSequence publicada a un grupo o a un estudiante puntual")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/learning-assignments")
public class LearningAssignmentController {

    private final CrearLearningAssignmentUseCase crearLearningAssignmentUseCase;
    private final ActualizarLearningAssignmentUseCase actualizarLearningAssignmentUseCase;
    private final ConsultarLearningAssignmentUseCase consultarLearningAssignmentUseCase;
    private final ListarLearningAssignmentUseCase listarLearningAssignmentUseCase;
    private final EliminarLearningAssignmentUseCase eliminarLearningAssignmentUseCase;
    private final CancelarLearningAssignmentUseCase cancelarLearningAssignmentUseCase;

    @Operation(summary = "Crear asignación", description = "Asigna una secuencia publicada a un grupo o a un estudiante puntual")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Asignación creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningAssignment.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<LearningAssignment> crear(@Valid @RequestBody CrearLearningAssignmentDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearLearningAssignmentUseCase.crear(request));
    }

    @Operation(summary = "Listar / buscar asignaciones",
            description = "Filtros opcionales: sequenceId, grupoId, estudianteId, estado. Sin filtros devuelve todas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de asignaciones",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LearningAssignment.class))))
    })
    @GetMapping
    public ResponseEntity<List<LearningAssignment>> listar(
            @RequestParam(required = false) Long sequenceId,
            @RequestParam(required = false) Long grupoId,
            @RequestParam(required = false) Long estudianteId,
            @RequestParam(required = false) EstadoLearningAssignment estado) {
        FiltroLearningAssignmentDto filtro = FiltroLearningAssignmentDto.builder()
                .sequenceId(sequenceId)
                .grupoId(grupoId)
                .estudianteId(estudianteId)
                .estado(estado)
                .build();
        return ResponseEntity.ok(listarLearningAssignmentUseCase.listar(filtro));
    }

    @Operation(summary = "Consultar asignación", description = "Obtiene una asignación por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningAssignment.class))),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content)
    })
    @GetMapping("/{assignmentId}")
    public ResponseEntity<LearningAssignment> consultar(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(consultarLearningAssignmentUseCase.consultarPorId(assignmentId));
    }

    @Operation(summary = "Actualizar asignación", description = "Actualiza la ventana de disponibilidad/entrega de una asignación existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningAssignment.class))),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content)
    })
    @PutMapping("/{assignmentId}")
    public ResponseEntity<LearningAssignment> actualizar(@PathVariable Long assignmentId,
                                                          @Valid @RequestBody ActualizarLearningAssignmentDto request) {
        request.setId(assignmentId);
        return ResponseEntity.ok(actualizarLearningAssignmentUseCase.actualizar(request));
    }

    @Operation(summary = "Eliminar asignación", description = "Elimina (soft-delete) una asignación existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Asignación eliminada"),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content)
    })
    @DeleteMapping("/{assignmentId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long assignmentId) {
        eliminarLearningAssignmentUseCase.eliminar(assignmentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancelar asignación", description = "Marca la asignación como CANCELLED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asignación cancelada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningAssignment.class))),
            @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content)
    })
    @PostMapping("/{assignmentId}/cancel")
    public ResponseEntity<LearningAssignment> cancelar(@PathVariable Long assignmentId) {
        return ResponseEntity.ok(cancelarLearningAssignmentUseCase.cancelar(assignmentId));
    }
}
