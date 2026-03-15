package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ActualizarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ConsultarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.CrearAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.EliminarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona los acudientes")
@Tag(name = "Acudientes", description = "Gestiona los acudientes")
@RestController
@RequestMapping("/gestion-alumnos/acudientes")
public class AcudienteController {

    private final CrearAcudienteUseCase crearUseCase;
    private final ActualizarAcudienteUseCase actualizarUseCase;
    private final ConsultarAcudienteUseCase consultarUseCase;
    private final ListarAcudientesUseCase listarUseCase;
    private final ListarAcudientesPorEstudianteUseCase listarPorEstudianteUseCase;
    private final EliminarAcudienteUseCase eliminarUseCase;

    public AcudienteController(CrearAcudienteUseCase crearUseCase,
                               ActualizarAcudienteUseCase actualizarUseCase,
                               ConsultarAcudienteUseCase consultarUseCase,
                               ListarAcudientesUseCase listarUseCase,
                               ListarAcudientesPorEstudianteUseCase listarPorEstudianteUseCase,
                               EliminarAcudienteUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.listarPorEstudianteUseCase = listarPorEstudianteUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @Operation(summary = "Crear acudiente", description = "Registra un nuevo acudiente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Acudiente creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AcudienteDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AcudienteDto> crear(@RequestBody CrearAcudienteDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @Operation(summary = "Actualizar acudiente", description = "Actualiza los datos de un acudiente existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acudiente actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AcudienteDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Acudiente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{acudienteId}")
    public ResponseEntity<AcudienteDto> actualizar(@PathVariable Long acudienteId,
                                                   @RequestBody ActualizarAcudienteDto request) {
        ActualizarAcudienteDto payload = new ActualizarAcudienteDto(
                acudienteId,
                request.estudianteId(),
                request.tipoDocumento(),
                request.numeroDocumento(),
                request.nombres(),
                request.apellidos(),
                request.parentesco(),
                request.telefono(),
                request.correo(),
                request.esPrincipal()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(payload));
    }

    @Operation(summary = "Consultar acudiente", description = "Obtiene la información de un acudiente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acudiente encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AcudienteDto.class))),
            @ApiResponse(responseCode = "404", description = "Acudiente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{acudienteId}")
    public ResponseEntity<AcudienteDto> consultar(@PathVariable Long acudienteId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(acudienteId));
    }

    @Operation(summary = "Listar acudientes", description = "Obtiene todos los acudientes registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de acudientes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AcudienteDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AcudienteDto>> listar() {
        return ResponseEntity.ok(listarUseCase.listar());
    }

    @Operation(summary = "Listar por estudiante", description = "Obtiene los acudientes asociados a un estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Acudientes del estudiante",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AcudienteDto.class)))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<AcudienteDto>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(listarPorEstudianteUseCase.listar(estudianteId));
    }

    @Operation(summary = "Eliminar acudiente", description = "Elimina un acudiente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Acudiente eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Acudiente no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{acudienteId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long acudienteId) {
        eliminarUseCase.eliminar(acudienteId);
        return ResponseEntity.noContent().build();
    }
}
