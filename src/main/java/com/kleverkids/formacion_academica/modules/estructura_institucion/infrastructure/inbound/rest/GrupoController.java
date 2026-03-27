package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ActualizarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ConsultarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.CrearGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.EliminarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ListarGruposUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;
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

@Description(value = "Gestiona los grupos")
@Tag(name = "Grupos", description = "Gestiona los grupos")
@RestController
@RequestMapping("/estructura-institucion/grupos")
public class GrupoController {

    private final CrearGrupoUseCase crearGrupoUseCase;
    private final ActualizarGrupoUseCase actualizarGrupoUseCase;
    private final ConsultarGrupoUseCase consultarGrupoUseCase;
    private final ListarGruposUseCase listarGruposUseCase;
    private final EliminarGrupoUseCase eliminarGrupoUseCase;

    public GrupoController(CrearGrupoUseCase crearGrupoUseCase,
                           ActualizarGrupoUseCase actualizarGrupoUseCase,
                           ConsultarGrupoUseCase consultarGrupoUseCase,
                           ListarGruposUseCase listarGruposUseCase,
                           EliminarGrupoUseCase eliminarGrupoUseCase) {
        this.crearGrupoUseCase = crearGrupoUseCase;
        this.actualizarGrupoUseCase = actualizarGrupoUseCase;
        this.consultarGrupoUseCase = consultarGrupoUseCase;
        this.listarGruposUseCase = listarGruposUseCase;
        this.eliminarGrupoUseCase = eliminarGrupoUseCase;
    }

    @Operation(summary = "Crear grupo", description = "Crea un nuevo grupo académico")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grupo creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Grupo.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Grupo> crear(@RequestBody CrearGrupoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearGrupoUseCase.crear(request));
    }

    @Operation(summary = "Actualizar grupo", description = "Actualiza los datos de un grupo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Grupo.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grupo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{grupoId}")
    public ResponseEntity<Grupo> actualizar(@PathVariable Long grupoId,
                                               @RequestBody ActualizarGrupoDto request) {
        ActualizarGrupoDto payload = ActualizarGrupoDto.builder()
                .id(grupoId)
                .nombre(request.getNombre())
                .capacidadMaxima(request.getCapacidadMaxima())
                .tutorId(request.getTutorId())
                .aulaId(request.getAulaId())
                .build();
        return ResponseEntity.ok(actualizarGrupoUseCase.actualizar(payload));
    }

    @Operation(summary = "Consultar grupo", description = "Obtiene la información de un grupo por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Grupo.class))),
            @ApiResponse(responseCode = "404", description = "Grupo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{grupoId}")
    public ResponseEntity<Grupo> consultar(@PathVariable Long grupoId) {
        return ResponseEntity.ok(consultarGrupoUseCase.consultarPorId(grupoId));
    }

    @Operation(summary = "Listar grupos", description = "Retorna todos los grupos registrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de grupos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Grupo.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Grupo>> listar() {
        return ResponseEntity.ok(listarGruposUseCase.listar());
    }

    @Operation(summary = "Eliminar grupo", description = "Elimina un grupo existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grupo no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long grupoId) {
        eliminarGrupoUseCase.eliminar(grupoId);
        return ResponseEntity.noContent().build();
    }
}
