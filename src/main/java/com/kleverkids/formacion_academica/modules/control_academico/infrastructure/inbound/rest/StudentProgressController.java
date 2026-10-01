package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.student_progress.ConsultarProgresoEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.student_progress.ConsultarProgresoSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Consulta el progreso de un estudiante (o de todos los asignados) en una secuencia de aprendizaje")
@Tag(name = "Progreso de secuencias", description = "Proyección recalculable a partir de los intentos del estudiante — ver StudentSequenceProgress")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/student-progress")
public class StudentProgressController {

    private final ConsultarProgresoEstudianteUseCase consultarProgresoEstudianteUseCase;
    private final ConsultarProgresoSequenceUseCase consultarProgresoSequenceUseCase;

    @Operation(summary = "Progreso de un estudiante en una secuencia",
            description = "Si no existe una proyección guardada todavía, se calcula en el momento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Progreso del estudiante",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StudentSequenceProgress.class)))
    })
    @GetMapping("/students/{estudianteId}/sequences/{sequenceId}")
    public ResponseEntity<StudentSequenceProgress> consultar(@PathVariable Long estudianteId, @PathVariable Long sequenceId) {
        return ResponseEntity.ok(consultarProgresoEstudianteUseCase.consultar(estudianteId, sequenceId));
    }

    @Operation(summary = "Progreso de todos los estudiantes en una secuencia", description = "Vista docente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Progreso por estudiante",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = StudentSequenceProgress.class))))
    })
    @GetMapping("/sequences/{sequenceId}")
    public ResponseEntity<List<StudentSequenceProgress>> consultarPorSecuencia(@PathVariable Long sequenceId) {
        return ResponseEntity.ok(consultarProgresoSequenceUseCase.consultarPorSecuencia(sequenceId));
    }
}
