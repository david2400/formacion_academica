package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.FinalizarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.IniciarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.ListarIntentosPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.RegistrarRespuestaIntentoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Description(value = "Gestiona los intentos de examen de un estudiante")
@Tag(name = "Intentos de Examen", description = "Gestiona los intentos de examen de un estudiante")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/examenes/{examenId}/estudiantes/{estudianteId}/intentos")
public class IntentoExamenController {

    private final IniciarIntentoExamenUseCase iniciarUseCase;
    private final RegistrarRespuestaIntentoUseCase registrarRespuestaUseCase;
    private final FinalizarIntentoExamenUseCase finalizarUseCase;
    private final ListarIntentosPorEstudianteUseCase listarUseCase;

    @Operation(summary = "Iniciar intento", description = "Crea un nuevo intento de examen para el estudiante")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Intento iniciado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IntentoExamenDto.class))),
            @ApiResponse(responseCode = "404", description = "Examen o estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping
    public ResponseEntity<IntentoExamenDto> iniciar(@PathVariable Long examenId,
                                                    @PathVariable Long estudianteId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(iniciarUseCase.iniciar(new IniciarIntentoExamenDto(examenId, estudianteId)));
    }

    @Operation(summary = "Registrar respuesta", description = "Registra la respuesta de un intento en progreso")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Respuesta registrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RespuestaIntentoDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Intento no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/{intentoId}/respuestas")
    public ResponseEntity<RespuestaIntentoDto> registrarRespuesta(@PathVariable Long intentoId,
                                                                  @Valid @RequestBody RegistrarRespuestaIntentoDto request) {
        request.setIntentoId(intentoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrarRespuestaUseCase.registrar(request));
    }

    @Operation(summary = "Finalizar intento", description = "Marca un intento como finalizado y opcionalmente registra el puntaje")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Intento finalizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = IntentoExamenDto.class))),
            @ApiResponse(responseCode = "404", description = "Intento no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @PostMapping("/{intentoId}/finalizar")
    public ResponseEntity<IntentoExamenDto> finalizar(@PathVariable Long intentoId,
                                                      @RequestBody(required = false) FinalizarIntentoExamenDto request) {
        Integer puntajeTotal = request != null ? request.puntajeTotal() : null;
        return ResponseEntity.ok(finalizarUseCase.finalizar(new FinalizarIntentoExamenDto(intentoId, puntajeTotal)));
    }

    @Operation(summary = "Listar intentos", description = "Obtiene los intentos de un estudiante para un examen")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de intentos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = IntentoExamenDto.class)))),
            @ApiResponse(responseCode = "404", description = "Examen o estudiante no encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<IntentoExamenDto>> listar(@PathVariable Long examenId,
                                                         @PathVariable Long estudianteId) {
        return ResponseEntity.ok(listarUseCase.listar(examenId, estudianteId));
    }
}
