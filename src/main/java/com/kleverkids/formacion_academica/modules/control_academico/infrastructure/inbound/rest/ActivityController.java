package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ActualizarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.AgregarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ArchivarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ConsultarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.CrearActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.DuplicarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.EliminarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.EliminarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ListarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ListarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.PublicarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ReordenarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ActualizarActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.AgregarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.CrearActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.FiltroActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ReordenarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Description(value = "Gestiona las actividades de aprendizaje")
@Tag(name = "Actividades", description = "CRUD, ciclo de vida (publicar/archivar/duplicar) y contenido de actividades")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/activities")
public class ActivityController {

    private final CrearActivityUseCase crearActivityUseCase;
    private final ActualizarActivityUseCase actualizarActivityUseCase;
    private final ConsultarActivityUseCase consultarActivityUseCase;
    private final ListarActivityUseCase listarActivityUseCase;
    private final EliminarActivityUseCase eliminarActivityUseCase;
    private final PublicarActivityUseCase publicarActivityUseCase;
    private final ArchivarActivityUseCase archivarActivityUseCase;
    private final DuplicarActivityUseCase duplicarActivityUseCase;
    private final AgregarContenidoActivityUseCase agregarContenidoActivityUseCase;
    private final ListarContenidoActivityUseCase listarContenidoActivityUseCase;
    private final EliminarContenidoActivityUseCase eliminarContenidoActivityUseCase;
    private final ReordenarContenidoActivityUseCase reordenarContenidoActivityUseCase;

    @Operation(summary = "Crear actividad", description = "Registra una nueva actividad en estado DRAFT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Actividad creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Activity> crear(@Valid @RequestBody CrearActivityDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearActivityUseCase.crear(request));
    }

    @Operation(summary = "Listar / buscar actividades",
            description = "Filtros opcionales: texto (título), activityTypeId, estado. Sin filtros devuelve todas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de actividades",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Activity.class))))
    })
    @GetMapping
    public ResponseEntity<List<Activity>> listar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) Long activityTypeId,
            @RequestParam(required = false) EstadoActivity estado) {
        return ResponseEntity.ok(listarActivityUseCase.buscar(new FiltroActivityDto(texto, activityTypeId, estado)));
    }

    @Operation(summary = "Consultar actividad", description = "Obtiene una actividad por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    @GetMapping("/{activityId}")
    public ResponseEntity<Activity> consultar(@PathVariable Long activityId) {
        return consultarActivityUseCase.consultarPorId(activityId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar actividad", description = "Actualiza los datos editables de una actividad (no su tipo)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    @PutMapping("/{activityId}")
    public ResponseEntity<Activity> actualizar(@PathVariable Long activityId,
                                               @Valid @RequestBody ActualizarActivityDto request) {
        request.setId(activityId);
        return ResponseEntity.ok(actualizarActivityUseCase.actualizar(request));
    }

    @Operation(summary = "Eliminar actividad", description = "Elimina (soft-delete) una actividad existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Actividad eliminada"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long activityId) {
        eliminarActivityUseCase.eliminar(activityId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Publicar actividad",
            description = """
                    Valida (según el tipo de actividad) que tenga contenido suficiente,
                    congela una nueva ActivityVersion y marca la actividad como PUBLISHED.
                    Quien ya tenga una versión anterior asignada no se ve afectado.
                    """)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad publicada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "400", description = "La actividad no tiene contenido válido para publicarse", content = @Content),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    @PostMapping("/{activityId}/publish")
    public ResponseEntity<Activity> publicar(@PathVariable Long activityId) {
        return ResponseEntity.ok(publicarActivityUseCase.publicar(activityId));
    }

    @Operation(summary = "Archivar actividad", description = "Marca la actividad como ARCHIVED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Actividad archivada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    @PostMapping("/{activityId}/archive")
    public ResponseEntity<Activity> archivar(@PathVariable Long activityId) {
        return ResponseEntity.ok(archivarActivityUseCase.archivar(activityId));
    }

    @Operation(summary = "Duplicar actividad", description = "Crea una copia DRAFT de la actividad y su contenido actual")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Actividad duplicada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Activity.class))),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    @PostMapping("/{activityId}/duplicate")
    public ResponseEntity<Activity> duplicar(@PathVariable Long activityId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(duplicarActivityUseCase.duplicar(activityId));
    }

    @Operation(summary = "Agregar contenido", description = "Agrega una pregunta existente, un bloque de texto, o contenido externo (video, embed o enlace) al final de la actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contenido agregado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityContentItem.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (p.ej. pregunta inexistente)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada", content = @Content)
    })
    @PostMapping("/{activityId}/content-items")
    public ResponseEntity<ActivityContentItem> agregarContenido(@PathVariable Long activityId,
                                                                 @Valid @RequestBody AgregarContenidoActivityDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agregarContenidoActivityUseCase.agregarContenido(activityId, request));
    }

    @Operation(summary = "Listar contenido", description = "Devuelve el contenido de la actividad en orden")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contenido de la actividad",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivityContentItem.class))))
    })
    @GetMapping("/{activityId}/content-items")
    public ResponseEntity<List<ActivityContentItem>> listarContenido(@PathVariable Long activityId) {
        return ResponseEntity.ok(listarContenidoActivityUseCase.listarContenido(activityId));
    }

    @Operation(summary = "Eliminar contenido", description = "Elimina una pieza de contenido de la actividad")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contenido eliminado")
    })
    @DeleteMapping("/{activityId}/content-items/{itemId}")
    public ResponseEntity<Void> eliminarContenido(@PathVariable Long activityId, @PathVariable Long itemId) {
        eliminarContenidoActivityUseCase.eliminarContenido(activityId, itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reordenar contenido", description = "Actualiza el orden de las piezas de contenido (drag & drop)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contenido reordenado",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivityContentItem.class))))
    })
    @PutMapping("/{activityId}/content-items/reorder")
    public ResponseEntity<List<ActivityContentItem>> reordenarContenido(@PathVariable Long activityId,
                                                                        @Valid @RequestBody ReordenarContenidoActivityDto request) {
        return ResponseEntity.ok(reordenarContenidoActivityUseCase.reordenar(activityId, request));
    }
}
