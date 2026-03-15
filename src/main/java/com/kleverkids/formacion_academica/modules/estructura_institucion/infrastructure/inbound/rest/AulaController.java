package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ActualizarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ConsultarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.CrearAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.EliminarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ListarAulasUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
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

@Description(value = "Gestiona las aulas")
@Tag(name = "Aulas", description = "Gestiona las aulas")
@RestController
@RequestMapping("/estructura-institucion/aulas")
public class AulaController {

    private final CrearAulaUseCase crearAulaUseCase;
    private final ActualizarAulaUseCase actualizarAulaUseCase;
    private final ListarAulasUseCase listarAulasUseCase;
    private final ConsultarAulaUseCase consultarAulaUseCase;
    private final EliminarAulaUseCase eliminarAulaUseCase;

    public AulaController(CrearAulaUseCase crearAulaUseCase,
                          ActualizarAulaUseCase actualizarAulaUseCase,
                          ListarAulasUseCase listarAulasUseCase,
                          ConsultarAulaUseCase consultarAulaUseCase,
                          EliminarAulaUseCase eliminarAulaUseCase) {
        this.crearAulaUseCase = crearAulaUseCase;
        this.actualizarAulaUseCase = actualizarAulaUseCase;
        this.listarAulasUseCase = listarAulasUseCase;
        this.consultarAulaUseCase = consultarAulaUseCase;
        this.eliminarAulaUseCase = eliminarAulaUseCase;
    }

    @Operation(summary = "Crear aula", description = "Registra una nueva aula dentro de la institución")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Aula creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AulaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AulaDto> crear(@RequestBody CrearAulaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearAulaUseCase.crear(request));
    }

    @Operation(summary = "Actualizar aula", description = "Actualiza los datos de un aula existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aula actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AulaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Aula no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{aulaId}")
    public ResponseEntity<AulaDto> actualizar(@PathVariable Long aulaId,
                                              @RequestBody ActualizarAulaDto request) {
        request.setId(aulaId);
        return ResponseEntity.ok(actualizarAulaUseCase.actualizar(request));
    }

    @Operation(summary = "Listar aulas", description = "Retorna todas las aulas registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de aulas",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AulaDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AulaDto>> listar() {
        return ResponseEntity.ok(listarAulasUseCase.listar());
    }

    @Operation(summary = "Consultar aula", description = "Obtiene la información de un aula por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aula encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AulaDto.class))),
            @ApiResponse(responseCode = "404", description = "Aula no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{aulaId}")
    public ResponseEntity<AulaDto> consultar(@PathVariable Long aulaId) {
        return ResponseEntity.ok(consultarAulaUseCase.consultarPorId(aulaId));
    }

    @Operation(summary = "Eliminar aula", description = "Elimina una aula existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Aula eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Aula no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{aulaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long aulaId) {
        eliminarAulaUseCase.eliminar(aulaId);
        return ResponseEntity.noContent().build();
    }
}
