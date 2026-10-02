package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth.AutenticarAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.auth.AutenticarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth.LoginResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Login local de Estudiante/Acudiente: documento + contraseña propia,
 * independiente de access_control (ver {@code Estudiante#getPassword()} /
 * {@code Acudiente#getPassword()}). No usa el mecanismo de access_control
 * porque ninguno de los dos es un {@code User} allí — ver el anexo "ETAPA 5B"
 * de la arquitectura del módulo de Actividades para el porqué completo.
 */
@Description(value = "Login de Estudiante y Acudiente (documento + contraseña propia, sin depender de access_control)")
@Tag(name = "Autenticación de Estudiante/Acudiente", description = "Login local para gestion_alumnos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/gestion-alumnos/auth")
public class AuthController {

    private final AutenticarEstudianteUseCase autenticarEstudianteUseCase;
    private final AutenticarAcudienteUseCase autenticarAcudienteUseCase;

    @Operation(summary = "Login de estudiante", description = "Autentica a un estudiante por tipo+número de documento y contraseña propia")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Documento o contraseña incorrectos", content = @Content)
    })
    @PostMapping("/estudiantes/login")
    public ResponseEntity<LoginResponseDto> loginEstudiante(@Valid @RequestBody LoginEstudianteDto request) {
        return ResponseEntity.ok(autenticarEstudianteUseCase.autenticar(request));
    }

    @Operation(summary = "Login de acudiente", description = "Autentica a un acudiente por tipo+número de documento y contraseña propia")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login exitoso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Documento o contraseña incorrectos", content = @Content)
    })
    @PostMapping("/acudientes/login")
    public ResponseEntity<LoginResponseDto> loginAcudiente(@Valid @RequestBody LoginAcudienteDto request) {
        return ResponseEntity.ok(autenticarAcudienteUseCase.autenticar(request));
    }
}
