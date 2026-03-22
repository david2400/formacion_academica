package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ActualizarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.CrearEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.EliminarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesPaginadoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@Description(value = "Gestiona los estudiantes")
@Tag(name = "Estudiantes", description = "Gestiona los estudiantes")
@RestController
@RequestMapping("/gestion-alumnos/estudiantes")
public class EstudianteController {

    private final CrearEstudianteUseCase crearUseCase;
    private final ActualizarEstudianteUseCase actualizarUseCase;
    private final ConsultarEstudianteUseCase consultarUseCase;
    private final ListarEstudiantesUseCase listarUseCase;
    private final ListarEstudiantesPaginadoUseCase listarPaginadoUseCase;
    private final EliminarEstudianteUseCase eliminarUseCase;

    public EstudianteController(CrearEstudianteUseCase crearUseCase,
                                ActualizarEstudianteUseCase actualizarUseCase,
                                ConsultarEstudianteUseCase consultarUseCase,
                                ListarEstudiantesUseCase listarUseCase,
                                ListarEstudiantesPaginadoUseCase listarPaginadoUseCase,
                                EliminarEstudianteUseCase eliminarUseCase) {
        this.crearUseCase = crearUseCase;
        this.actualizarUseCase = actualizarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.listarPaginadoUseCase = listarPaginadoUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @Operation(summary = "Crear estudiante", description = "Registra un nuevo estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Estudiante creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Estudiante> crear(@Valid @RequestBody CrearEstudianteDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearUseCase.crear(request));
    }

    @Operation(summary = "Actualizar estudiante", description = "Actualiza los datos de un estudiante existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{estudianteId}")
    public ResponseEntity<Estudiante> actualizar(@PathVariable Long estudianteId,
                                                    @Valid @RequestBody UpdateEstudianteDto request) {
        request.setId(estudianteId);
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Consultar estudiante", description = "Obtiene la información de un estudiante por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estudiante.class))),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{estudianteId}")
    public ResponseEntity<Estudiante> consultar(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(estudianteId));
    }

    @Operation(summary = "Listar estudiantes", description = "Obtiene el catálogo completo de estudiantes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de estudiantes",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Estudiante.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Estudiante>> listar() {
        return ResponseEntity.ok(listarUseCase.listar());
    }

    @Operation(summary = "Listar estudiantes paginados", description = "Obtiene estudiantes paginados según el pageable recibido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Página de estudiantes",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/paged")
    public ResponseEntity<Page<Estudiante>> listar(Pageable pageable) {
        return ResponseEntity.ok(listarPaginadoUseCase.listar(pageable));
    }

    @Operation(summary = "Eliminar estudiante", description = "Elimina un estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estudiante eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{estudianteId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long estudianteId) {
        eliminarUseCase.eliminar(estudianteId);
        return ResponseEntity.noContent().build();
    }
}
