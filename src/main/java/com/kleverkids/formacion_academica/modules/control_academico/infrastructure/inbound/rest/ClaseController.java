package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ActualizarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ConsultarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClasesMasivasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.EliminarClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ListarClasesUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.RegistrarSeguimientoClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ActualizarClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarSeguimientoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ResultadoClasesMasivasDto;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona las clases")
@Tag(name = "Clases", description = "Gestiona las clases")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/clases")
public class ClaseController {

    private final CrearClaseUseCase crearClaseUseCase;
    private final CrearClasesMasivasUseCase crearClasesMasivasUseCase;
    private final ConsultarClaseUseCase consultarClaseUseCase;
    private final ListarClasesUseCase listarClasesUseCase;
    private final ActualizarClaseUseCase actualizarClaseUseCase;
    private final RegistrarSeguimientoClaseUseCase registrarSeguimientoClaseUseCase;
    private final EliminarClaseUseCase eliminarClaseUseCase;

    @Operation(summary = "Crear clase", description = "Registra una nueva clase individual")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Clase creada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Clase> crearClase(@Valid @RequestBody CrearClaseDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearClaseUseCase.crearClase(request));
    }

    @Operation(summary = "Crear clases masivas", description = "Registra múltiples clases en un solo proceso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Clases creadas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResultadoClasesMasivasDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/masivas")
    public ResponseEntity<ResultadoClasesMasivasDto> crearClasesMasivas(@Valid @RequestBody CrearClasesMasivasDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearClasesMasivasUseCase.crearClases(request));
    }

    @Operation(summary = "Consultar clase", description = "Obtiene la información de una clase por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clase encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{claseId}")
    public ResponseEntity<Clase> consultarClase(@PathVariable Long claseId) {
        return consultarClaseUseCase.consultarPorId(claseId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar clases", description = "Obtiene el listado de todas las clases registradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de clases",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Clase.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Clase>> listarClases() {
        return ResponseEntity.ok(listarClasesUseCase.listarTodas());
    }

    @Operation(summary = "Actualizar clase", description = "Actualiza los datos de una clase existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Clase actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{claseId}")
    public ResponseEntity<Clase> actualizarClase(@PathVariable Long claseId,
                                                 @Valid @RequestBody ActualizarClaseDto request) {
        return ResponseEntity.ok(actualizarClaseUseCase.actualizar(request));
    }

    @Operation(
            summary = "Registrar seguimiento de la clase",
            description = """
                    Actualización parcial pensada para el calendario: marca la clase como
                    dictada o cancelada y/o registra observaciones, sin reenviar la clase
                    completa. Los campos nulos se dejan como estaban.

                    Estados válidos: programada, dictada, cancelada.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Seguimiento registrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Clase.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PatchMapping("/{claseId}/seguimiento")
    public ResponseEntity<Clase> registrarSeguimiento(@PathVariable Long claseId,
                                                      @RequestBody RegistrarSeguimientoClaseDto request) {
        return ResponseEntity.ok(registrarSeguimientoClaseUseCase.registrarSeguimiento(claseId, request));
    }

    @Operation(summary = "Eliminar clase", description = "Elimina una clase existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Clase eliminada"),
            @ApiResponse(responseCode = "404", description = "Clase no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{claseId}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long claseId) {
        eliminarClaseUseCase.eliminar(claseId);
        return ResponseEntity.noContent().build();
    }
}
