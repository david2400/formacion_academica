package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.CrearClasesMasivasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ResultadoClasesMasivasDto;
import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Description(value = "Gestiona las clases")
@Tag(name = "Clases", description = "Gestiona las clases")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/clases")
public class ClaseController {

    private final CrearClaseUseCase crearClaseUseCase;
    private final CrearClasesMasivasUseCase crearClasesMasivasUseCase;



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
}
