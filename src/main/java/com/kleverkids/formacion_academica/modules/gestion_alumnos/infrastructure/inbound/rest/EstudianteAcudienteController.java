package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ActualizarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ConsultarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.CrearEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.EliminarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ListarPorAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ListarPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Description(value = "Gestiona la relación entre estudiantes y acudientes")
@Tag(name = "Estudiante - Acudiente", description = "Gestiona la relación entre estudiantes y acudientes")
@RestController
@RequestMapping("/gestion-alumnos/relaciones-estudiante-acudiente")
public class EstudianteAcudienteController {

    private final CrearEstudianteAcudienteUseCase crearUseCase;
    private final ActualizarEstudianteAcudienteUseCase actualizarUseCase;
    private final ConsultarEstudianteAcudienteUseCase consultarUseCase;
    private final ListarPorEstudianteUseCase listarPorEstudianteUseCase;
    private final ListarPorAcudienteUseCase listarPorAcudienteUseCase;
    private final EliminarEstudianteAcudienteUseCase eliminarUseCase;

    public EstudianteAcudienteController(CrearEstudianteAcudienteUseCase crearUseCase,
                                         ActualizarEstudianteAcudienteUseCase actualizarUseCase,
                                         ConsultarEstudianteAcudienteUseCase consultarUseCase,
                                         ListarPorEstudianteUseCase listarPorEstudianteUseCase,
                                         ListarPorAcudienteUseCase listarPorAcudienteUseCase,
                                         EliminarEstudianteAcudienteUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarPorEstudianteUseCase = listarPorEstudianteUseCase;
        this.listarPorAcudienteUseCase = listarPorAcudienteUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @Operation(summary = "Crear relación estudiante-acudiente", description = "Crea una relación entre estudiante y acudiente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Relación creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstudianteAcudienteDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EstudianteAcudienteDto> crear(@RequestBody CrearEstudianteAcudienteDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @Operation(summary = "Actualizar relación", description = "Actualiza los datos de una relación estudiante-acudiente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relación actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstudianteAcudienteDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Relación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{relacionId}")
    public ResponseEntity<EstudianteAcudienteDto> actualizar(@PathVariable Long relacionId,
                                                                     @RequestBody ActualizarEstudianteAcudienteDto request) {
        ActualizarEstudianteAcudienteDto payload = new ActualizarEstudianteAcudienteDto(
                relacionId,
                request.estudianteId(),
                request.parentesco(),
                request.esPrincipal(),
                request.estado()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @Operation(summary = "Consultar relación", description = "Obtiene la información de una relación específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relación encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EstudianteAcudienteDto.class))),
            @ApiResponse(responseCode = "404", description = "Relación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{relacionId}")
    public ResponseEntity<EstudianteAcudienteDto> consultar(@PathVariable Long relacionId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(relacionId));
    }

    @Operation(summary = "Listar relaciones por estudiante", description = "Obtiene las relaciones de un estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relaciones del estudiante",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EstudianteAcudienteDto.class)))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<EstudianteAcudienteDto>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(listarPorEstudianteUseCase.listarPorEstudiante(estudianteId));
    }

    @Operation(summary = "Listar relaciones por acudiente", description = "Obtiene los estudiantes asociados a un acudiente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Relaciones del acudiente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EstudianteAcudienteDto.class)))),
            @ApiResponse(responseCode = "404", description = "Acudiente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<EstudianteAcudienteDto>> listarPorAcudiente(@RequestParam Long acudienteId) {
        return ResponseEntity.ok(listarPorAcudienteUseCase.listarPorAcudiente(acudienteId));
    }

    @Operation(summary = "Eliminar relación", description = "Elimina una relación estudiante-acudiente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Relación eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Relación no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{relacionId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long relacionId) {
        eliminarUseCase.eliminar(relacionId);
        return ResponseEntity.noContent().build();
    }
}
