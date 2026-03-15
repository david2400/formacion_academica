package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ActualizarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarHistorialAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.EliminarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ListarAsistenciasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.RegistrarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.ActualizarAsistenciaDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona la asistencia de los estudiantes")
@Tag(name = "Asistencias", description = "Gestiona la asistencia de los estudiantes")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/asistencias")
public class AsistenciaController {

    private final ActualizarAsistenciaUseCase actualizarUseCase;
    private final ConsultarAsistenciaUseCase consultarUseCase;
    private final EliminarAsistenciaUseCase eliminarUseCase;
    private final ListarAsistenciasUseCase listarUseCase;
    private final RegistrarAsistenciaUseCase registrarAsistenciaUseCase;
    private final ConsultarHistorialAsistenciaUseCase historialAsistenciaUseCase;

    @Operation(summary = "Registrar asistencia", description = "Registra una nueva asistencia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Asistencia registrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsistenciaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AsistenciaDto> registrar(@Valid @RequestBody RegistrarAsistenciaDto request) {
        AsistenciaDto asistencia = registrarAsistenciaUseCase.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(asistencia);
    }

    @Operation(summary = "Consultar asistencia", description = "Obtiene los detalles de una asistencia específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asistencia encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsistenciaDto.class))),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/{asistenciaId}")
    public ResponseEntity<AsistenciaDto> consultar(@PathVariable Long asistenciaId) {
        return ResponseEntity.ok(consultarUseCase.consultarPorId(asistenciaId));
    }

    @Operation(summary = "Listar asistencias", description = "Obtiene el listado completo de asistencias")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de asistencias",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AsistenciaDto.class)))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AsistenciaDto>> listar() {
        return ResponseEntity.ok(listarUseCase.listar());
    }

    @Operation(summary = "Actualizar asistencia", description = "Actualiza la información de una asistencia existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Asistencia actualizada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AsistenciaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PutMapping("/{asistenciaId}")
    public ResponseEntity<AsistenciaDto> actualizar(@PathVariable Long asistenciaId,
                                                    @RequestBody ActualizarAsistenciaDto request) {
        request = new ActualizarAsistenciaDto(
            asistenciaId,
            request.estudianteId(),
            request.claseId(),
            request.fecha(),
            request.estado(),
            request.observaciones()
        );
        return ResponseEntity.ok(actualizarUseCase.actualizar(request));
    }

    @Operation(summary = "Eliminar asistencia", description = "Elimina un registro de asistencia")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Asistencia eliminada", content = @Content),
            @ApiResponse(responseCode = "404", description = "Asistencia no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @DeleteMapping("/{asistenciaId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long asistenciaId) {
        eliminarUseCase.eliminar(asistenciaId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Consultar historial", description = "Devuelve el historial de asistencia filtrado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Historial obtenido",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = HistorialAsistenciaDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping("/historial")
    public ResponseEntity<HistorialAsistenciaDto> consultarHistorial(@RequestBody HistorialAsistenciaFiltroDto filtro) {
        return ResponseEntity.ok(historialAsistenciaUseCase.consultar(filtro));
    }
}
