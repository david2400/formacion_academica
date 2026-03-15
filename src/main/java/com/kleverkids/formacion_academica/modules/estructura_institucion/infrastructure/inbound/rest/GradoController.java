package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ActualizarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ConsultarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.CrearGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.EliminarGradoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado.ListarGradosUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@Description(value = "Gestiona los grados")
@Tag(name = "Grados", description = "Gestiona los grados")
@RestController
@RequestMapping("/estructura-institucion/grados")
public class GradoController {

    private final CrearGradoUseCase crearGradoUseCase;
    private final ActualizarGradoUseCase actualizarGradoUseCase;
    private final ConsultarGradoUseCase consultarGradoUseCase;
    private final ListarGradosUseCase listarGradosUseCase;
    private final EliminarGradoUseCase eliminarGradoUseCase;

    public GradoController(CrearGradoUseCase crearGradoUseCase,
                           ActualizarGradoUseCase actualizarGradoUseCase,
                           ConsultarGradoUseCase consultarGradoUseCase,
                           ListarGradosUseCase listarGradosUseCase,
                           EliminarGradoUseCase eliminarGradoUseCase) {
        this.crearGradoUseCase = crearGradoUseCase;
        this.actualizarGradoUseCase = actualizarGradoUseCase;
        this.consultarGradoUseCase = consultarGradoUseCase;
        this.listarGradosUseCase = listarGradosUseCase;
        this.eliminarGradoUseCase = eliminarGradoUseCase;
    }

    @Operation(summary = "Crear grado", description = "Crea un nuevo grado dentro de la institución")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Grado creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GradoDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<GradoDto> crear(@Valid @RequestBody CrearGradoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearGradoUseCase.crear(request));
    }

    @Operation(summary = "Actualizar grado", description = "Actualiza los datos de un grado existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grado actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GradoDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{gradoId}")
    public ResponseEntity<GradoDto> actualizar(@PathVariable Long gradoId,
                                               @Valid @RequestBody ActualizarGradoDto request) {
        request.setId(gradoId);
        return ResponseEntity.ok(actualizarGradoUseCase.actualizar(request));
    }

    @Operation(summary = "Consultar grado", description = "Obtiene la información de un grado por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grado encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GradoDto.class))),
            @ApiResponse(responseCode = "404", description = "Grado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{gradoId}")
    public ResponseEntity<GradoDto> consultar(@PathVariable Long gradoId) {
        return ResponseEntity.ok(consultarGradoUseCase.consultarPorId(gradoId));
    }

    @Operation(summary = "Listar grados", description = "Retorna el catálogo completo de grados disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de grados",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GradoDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<GradoDto>> listar() {
        return ResponseEntity.ok(listarGradosUseCase.listar());
    }

    @Operation(summary = "Eliminar grado", description = "Elimina un grado existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Grado eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Grado no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{gradoId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long gradoId) {
        eliminarGradoUseCase.eliminar(gradoId);
        return ResponseEntity.noContent().build();
    }
}
