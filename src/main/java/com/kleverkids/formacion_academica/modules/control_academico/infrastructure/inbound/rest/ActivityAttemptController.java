package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.CompletarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.ConsultarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.IniciarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.ListarActivityAttemptsUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.RegistrarRespuestaAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.IniciarActivityAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.RegistrarRespuestaAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona los intentos de un estudiante sobre una actividad")
@Tag(name = "Intentos de actividad", description = "Iniciar, responder y completar un intento; conecta ServicioValidacionRespuesta con el ciclo de vida de la actividad")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/activity-attempts")
public class ActivityAttemptController {

    private final IniciarActivityAttemptUseCase iniciarActivityAttemptUseCase;
    private final RegistrarRespuestaAttemptUseCase registrarRespuestaAttemptUseCase;
    private final CompletarActivityAttemptUseCase completarActivityAttemptUseCase;
    private final ConsultarActivityAttemptUseCase consultarActivityAttemptUseCase;
    private final ListarActivityAttemptsUseCase listarActivityAttemptsUseCase;

    @Operation(summary = "Iniciar intento",
            description = """
                    Valida que la actividad esté publicada, la ventana de la asignación (si aplica),
                    el máximo de intentos y las dependencias de la secuencia. Si ya existe un intento
                    IN_PROGRESS para la misma combinación actividad/asignación/estudiante, lo retoma
                    en vez de crear uno nuevo.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Intento iniciado (o retomado)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityAttempt.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "No se puede iniciar: ventana vencida, máximo de intentos alcanzado o dependencias pendientes", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ActivityAttempt> iniciar(@Valid @RequestBody IniciarActivityAttemptDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iniciarActivityAttemptUseCase.iniciar(request));
    }

    @Operation(summary = "Consultar intento", description = "Obtiene un intento por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Intento encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityAttempt.class))),
            @ApiResponse(responseCode = "404", description = "Intento no encontrado", content = @Content)
    })
    @GetMapping("/{attemptId}")
    public ResponseEntity<ActivityAttempt> consultar(@PathVariable Long attemptId) {
        return ResponseEntity.ok(consultarActivityAttemptUseCase.consultarPorId(attemptId));
    }

    @Operation(summary = "Listar intentos por estudiante y actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de intentos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivityAttempt.class))))
    })
    @GetMapping
    public ResponseEntity<List<ActivityAttempt>> listar(@RequestParam Long estudianteId, @RequestParam Long activityId) {
        return ResponseEntity.ok(listarActivityAttemptsUseCase.listarPorEstudianteYActividad(estudianteId, activityId));
    }

    @Operation(summary = "Registrar respuesta",
            description = "Califica la respuesta con ServicioValidacionRespuesta y la guarda (reemplaza la respuesta anterior para el mismo bloque de contenido, si existía)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuesta registrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityAttemptAnswer.class))),
            @ApiResponse(responseCode = "400", description = "El contenido no existe o no es una pregunta", content = @Content),
            @ApiResponse(responseCode = "409", description = "El intento ya finalizó", content = @Content)
    })
    @PostMapping("/{attemptId}/answers")
    public ResponseEntity<ActivityAttemptAnswer> registrarRespuesta(@PathVariable Long attemptId,
                                                                     @Valid @RequestBody RegistrarRespuestaAttemptDto request) {
        request.setAttemptId(attemptId);
        return ResponseEntity.ok(registrarRespuestaAttemptUseCase.registrarRespuesta(request));
    }

    @Operation(summary = "Listar respuestas de un intento")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Respuestas del intento",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivityAttemptAnswer.class))))
    })
    @GetMapping("/{attemptId}/answers")
    public ResponseEntity<List<ActivityAttemptAnswer>> listarRespuestas(@PathVariable Long attemptId) {
        return ResponseEntity.ok(consultarActivityAttemptUseCase.listarRespuestas(attemptId));
    }

    @Operation(summary = "Completar intento",
            description = "Verifica (según el tipo de actividad) que esté todo respondido, calcula el resultado agregado y marca el intento como COMPLETED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Intento completado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityAttempt.class))),
            @ApiResponse(responseCode = "409", description = "Aún faltan respuestas o el intento ya finalizó", content = @Content)
    })
    @PostMapping("/{attemptId}/complete")
    public ResponseEntity<ActivityAttempt> completar(@PathVariable Long attemptId) {
        return ResponseEntity.ok(completarActivityAttemptUseCase.completar(attemptId));
    }
}
