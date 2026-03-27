package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ActualizarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ConsultarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.CrearSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.EliminarSalonUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.salon.ListarSalonesUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.ActualizarSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon.CrearSalonDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
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
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Description(value = "Gestiona los salones")
@Tag(name = "Salones", description = "Gestiona los salones de la institución")
@RestController
@RequestMapping("/estructura-institucion/salones")
public class SalonController {

    private final CrearSalonUseCase crearSalonUseCase;
    private final ActualizarSalonUseCase actualizarSalonUseCase;
    private final ListarSalonesUseCase listarSalonesUseCase;
    private final ConsultarSalonUseCase consultarSalonUseCase;
    private final EliminarSalonUseCase eliminarSalonUseCase;

    public SalonController(CrearSalonUseCase crearSalonUseCase,
                          ActualizarSalonUseCase actualizarSalonUseCase,
                          ListarSalonesUseCase listarSalonesUseCase,
                          ConsultarSalonUseCase consultarSalonUseCase,
                          EliminarSalonUseCase eliminarSalonUseCase) {
        this.crearSalonUseCase = crearSalonUseCase;
        this.actualizarSalonUseCase = actualizarSalonUseCase;
        this.listarSalonesUseCase = listarSalonesUseCase;
        this.consultarSalonUseCase = consultarSalonUseCase;
        this.eliminarSalonUseCase = eliminarSalonUseCase;
    }

    @Operation(summary = "Crear salón", description = "Registra un nuevo salón dentro de la institución")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Salón creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Salon.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Salon> crear(@Valid @RequestBody CrearSalonDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearSalonUseCase.crear(request));
    }

    @Operation(summary = "Actualizar salón", description = "Actualiza los datos de un salón existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Salón actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Salon.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{salonId}")
    public ResponseEntity<Salon> actualizar(@PathVariable Long salonId,
                                              @Valid @RequestBody ActualizarSalonDto request) {
        // Crear un nuevo DTO con el ID del path y mantener los demás valores
        ActualizarSalonDto requestConId = new ActualizarSalonDto(
            salonId,
            request.codigo(),
            request.nombre(),
            request.descripcion(),
            request.capacidadMaxima(),
            request.numeroPiso(),
            request.tieneProyector(),
            request.tienePizarronBlanco(),
            request.tieneAireAcondicionado(),
            request.nombreEdificio(),
            request.activo()
        );
        return ResponseEntity.ok(actualizarSalonUseCase.actualizar(requestConId));
    }

    @Operation(summary = "Listar salones", description = "Retorna todos los salones registrados y activos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de salones",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Salon.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Salon>> listar() {
        return ResponseEntity.ok(listarSalonesUseCase.listar());
    }

    @Operation(summary = "Consultar salón", description = "Obtiene la información de un salón por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Salón encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Salon.class))),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{salonId}")
    public ResponseEntity<Salon> consultar(@PathVariable Long salonId) {
        return ResponseEntity.ok(consultarSalonUseCase.consultarPorId(salonId));
    }

    @Operation(summary = "Eliminar salón", description = "Elimina (desactiva) un salón existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Salón eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Salón no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{salonId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long salonId) {
        eliminarSalonUseCase.eliminar(salonId);
        return ResponseEntity.noContent().build();
    }
}
