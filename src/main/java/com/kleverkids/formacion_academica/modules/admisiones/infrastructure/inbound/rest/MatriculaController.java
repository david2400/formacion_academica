package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ActualizarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ConsultarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.EliminarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ListarMatriculasUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.RegistrarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
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

@Description(value = "Gestiona las matriculas de los alumnos")
@Tag(name = "Matricula")
@RestController
@RequestMapping("/admisiones/matriculas")
public class MatriculaController {

    private final ActualizarMatriculaUseCase actualizarUseCase;
    private final RegistrarMatriculaUseCase registrarUseCase;
    private final ConsultarMatriculaUseCase consultarUseCase;
    private final ListarMatriculasUseCase listarUseCase;
    private final EliminarMatriculaUseCase eliminarUseCase;

    public MatriculaController(ActualizarMatriculaUseCase actualizarUseCase,
                               RegistrarMatriculaUseCase registrarUseCase,
                               ConsultarMatriculaUseCase consultarUseCase,
                               ListarMatriculasUseCase listarUseCase,
                               EliminarMatriculaUseCase eliminarUseCase) {
        this.actualizarUseCase = actualizarUseCase;
        this.registrarUseCase = registrarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @Operation(summary = "Registrar matrícula", description = "Crea una nueva matrícula de estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Matrícula creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<MatriculaDto> registrar(@RequestBody CrearMatriculaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(request));
    }

    @Operation(summary = "Actualizar matrícula", description = "Actualiza los datos de una matrícula")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matrícula actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Matrícula no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{matriculaId}")
    public ResponseEntity<MatriculaDto> actualizar(@PathVariable Long matriculaId,
                                                  @RequestBody ActualizarMatriculaDto request) {
        request = new ActualizarMatriculaDto(
            matriculaId,
            request.estudianteId(),
            request.gradoId(),
            request.grupoId(),
            request.fechaMatricula(),
            request.estado(),
            request.observaciones()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Consultar matrícula", description = "Obtiene la información de una matrícula por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matrícula encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MatriculaDto.class))),
            @ApiResponse(responseCode = "404", description = "Matrícula no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{matriculaId}")
    public ResponseEntity<MatriculaDto> consultar(@PathVariable Long matriculaId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(matriculaId));
    }

    @Operation(summary = "Listar matrículas por estudiante", description = "Obtiene las matrículas de un estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de matrículas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MatriculaDto.class)))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<MatriculaDto>> listarPorEstudiante(@RequestParam Long estudianteId) {
        return ResponseEntity.ok(listarUseCase.listarPorEstudiante(estudianteId));
    }

    @Operation(summary = "Eliminar matrícula", description = "Elimina una matrícula existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Matrícula eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Matrícula no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{matriculaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long matriculaId) {
        eliminarUseCase.eliminar(matriculaId);
        return ResponseEntity.noContent().build();
    }
}
