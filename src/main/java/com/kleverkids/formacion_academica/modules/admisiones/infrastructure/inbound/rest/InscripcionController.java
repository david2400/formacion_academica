package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.CambiarEstadoInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ConsultarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.EliminarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.ListarInscripcionesUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.input.inscripcion.RegistrarInscripcionUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ListarInscripcionesFiltroDto;
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

@Description(value = "Gestiona las incripciones de los estudiantes")
@Tag(name = "Inscripciones")
@RestController
@RequestMapping("/admisiones/inscripciones")
public class InscripcionController {

    private final RegistrarInscripcionUseCase registrarUseCase;
    private final ConsultarInscripcionUseCase consultarUseCase;
    private final ListarInscripcionesUseCase listarUseCase;
    private final CambiarEstadoInscripcionUseCase cambiarEstadoUseCase;
    private final EliminarInscripcionUseCase eliminarUseCase;

    public InscripcionController(RegistrarInscripcionUseCase registrarUseCase,
                                 ConsultarInscripcionUseCase consultarUseCase,
                                 ListarInscripcionesUseCase listarUseCase,
                                 CambiarEstadoInscripcionUseCase cambiarEstadoUseCase,
                                 EliminarInscripcionUseCase eliminarUseCase) {
        this.registrarUseCase = registrarUseCase;
        this.consultarUseCase = consultarUseCase;
        this.listarUseCase = listarUseCase;
        this.cambiarEstadoUseCase = cambiarEstadoUseCase;
        this.eliminarUseCase = eliminarUseCase;
    }

    @Operation(summary = "Registrar inscripción", description = "Crea una nueva inscripción de estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inscripción creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inscripcion.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Inscripcion> registrar(@RequestBody CrearInscripcionDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarUseCase.registrar(request));
    }

    @Operation(summary = "Consultar inscripción", description = "Obtiene la información de una inscripción por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripción encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inscripcion.class))),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{inscripcionId}")
    public ResponseEntity<Inscripcion> consultar(@PathVariable Long inscripcionId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(inscripcionId));
    }

    @Operation(summary = "Listar inscripciones", description = "Obtiene inscripciones opcionalmente filtradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de inscripciones",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Inscripcion.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Inscripcion>> listar(@RequestBody(required = false) ListarInscripcionesFiltroDto filtro) {
        return ResponseEntity.ok(listarUseCase.listar(filtro));
    }

    @Operation(summary = "Cambiar estado", description = "Actualiza el estado de una inscripción")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inscripcion.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{inscripcionId}/estado")
    public ResponseEntity<Inscripcion> cambiarEstado(@PathVariable Long inscripcionId,
                                                        @RequestBody ActualizarEstadoInscripcionDto request) {
        return ResponseEntity.ok(cambiarEstadoUseCase.cambiarEstado(request));
    }

    @Operation(summary = "Eliminar inscripción", description = "Elimina una inscripción existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Inscripción eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{inscripcionId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long inscripcionId) {
        eliminarUseCase.eliminar(inscripcionId);
        return ResponseEntity.noContent().build();
    }
}
