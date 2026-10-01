package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ActualizarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ConsultarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.CrearActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ListarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.ActualizarActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.CrearActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona el catálogo extensible de tipos de actividad")
@Tag(name = "Tipos de actividad", description = "Registro extensible de tipos de actividad (QUESTION, QUESTION_GROUP, CONTENT, ...)")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/activity-types")
public class ActivityTypeDefinitionController {

    private final CrearActivityTypeDefinitionUseCase crearActivityTypeDefinitionUseCase;
    private final ActualizarActivityTypeDefinitionUseCase actualizarActivityTypeDefinitionUseCase;
    private final ConsultarActivityTypeDefinitionUseCase consultarActivityTypeDefinitionUseCase;
    private final ListarActivityTypeDefinitionUseCase listarActivityTypeDefinitionUseCase;

    @Operation(summary = "Crear tipo de actividad", description = "Registra un nuevo tipo en el catálogo extensible")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo creado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityTypeDefinition.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ActivityTypeDefinition> crear(@Valid @RequestBody CrearActivityTypeDefinitionDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearActivityTypeDefinitionUseCase.crear(request));
    }

    @Operation(summary = "Listar tipos de actividad",
            description = "Por defecto solo los activos (para el panel \"+ Agregar actividad\"); con incluirInactivos=true devuelve todo el catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de tipos",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivityTypeDefinition.class))))
    })
    @GetMapping
    public ResponseEntity<List<ActivityTypeDefinition>> listar(
            @RequestParam(required = false, defaultValue = "false") boolean incluirInactivos) {
        return ResponseEntity.ok(incluirInactivos
                ? listarActivityTypeDefinitionUseCase.listarTodos()
                : listarActivityTypeDefinitionUseCase.listarActivos());
    }

    @Operation(summary = "Consultar tipo de actividad", description = "Obtiene un tipo de actividad por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityTypeDefinition.class))),
            @ApiResponse(responseCode = "404", description = "Tipo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActivityTypeDefinition> consultar(@PathVariable Long id) {
        return consultarActivityTypeDefinitionUseCase.consultarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar tipo de actividad", description = "Actualiza un tipo existente (no permite cambiar su código)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo actualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ActivityTypeDefinition.class))),
            @ApiResponse(responseCode = "404", description = "Tipo no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActivityTypeDefinition> actualizar(@PathVariable Long id,
                                                             @Valid @RequestBody ActualizarActivityTypeDefinitionDto request) {
        request.setId(id);
        return ResponseEntity.ok(actualizarActivityTypeDefinitionUseCase.actualizar(request));
    }
}
