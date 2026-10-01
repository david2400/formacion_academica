package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ActualizarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.AgregarItemSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ArchivarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ConsultarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.CrearLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.DefinirDependenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.DuplicarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarDependenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarItemSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ListarDependenciasItemUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ListarItemsSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ListarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.PublicarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ReordenarItemsSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ActualizarLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.AgregarItemSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.CrearLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.DefinirDependenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.FiltroLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ReordenarItemsSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.EstadoLearningSequence;
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

@Description(value = "Gestiona las secuencias de aprendizaje (Etapa 2: items, agrupación, dependencias)")
@Tag(name = "Secuencias de aprendizaje", description = "CRUD, ciclo de vida, items/grupos y dependencias de desbloqueo")
@RequiredArgsConstructor
@RestController
@RequestMapping("/control-academico/learning-sequences")
public class LearningSequenceController {

    private final CrearLearningSequenceUseCase crearLearningSequenceUseCase;
    private final ActualizarLearningSequenceUseCase actualizarLearningSequenceUseCase;
    private final ConsultarLearningSequenceUseCase consultarLearningSequenceUseCase;
    private final ListarLearningSequenceUseCase listarLearningSequenceUseCase;
    private final EliminarLearningSequenceUseCase eliminarLearningSequenceUseCase;
    private final PublicarLearningSequenceUseCase publicarLearningSequenceUseCase;
    private final ArchivarLearningSequenceUseCase archivarLearningSequenceUseCase;
    private final DuplicarLearningSequenceUseCase duplicarLearningSequenceUseCase;
    private final AgregarItemSequenceUseCase agregarItemSequenceUseCase;
    private final ListarItemsSequenceUseCase listarItemsSequenceUseCase;
    private final EliminarItemSequenceUseCase eliminarItemSequenceUseCase;
    private final ReordenarItemsSequenceUseCase reordenarItemsSequenceUseCase;
    private final DefinirDependenciaUseCase definirDependenciaUseCase;
    private final ListarDependenciasItemUseCase listarDependenciasItemUseCase;
    private final EliminarDependenciaUseCase eliminarDependenciaUseCase;

    @Operation(summary = "Crear secuencia", description = "Registra una nueva secuencia de aprendizaje en estado DRAFT")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Secuencia creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningSequence.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<LearningSequence> crear(@Valid @RequestBody CrearLearningSequenceDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crearLearningSequenceUseCase.crear(request));
    }

    @Operation(summary = "Listar / buscar secuencias", description = "Filtros opcionales: texto (título), estado. Sin filtros devuelve todas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de secuencias",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LearningSequence.class))))
    })
    @GetMapping
    public ResponseEntity<List<LearningSequence>> listar(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) EstadoLearningSequence estado) {
        return ResponseEntity.ok(listarLearningSequenceUseCase.buscar(new FiltroLearningSequenceDto(texto, estado)));
    }

    @Operation(summary = "Consultar secuencia", description = "Obtiene una secuencia por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secuencia encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningSequence.class))),
            @ApiResponse(responseCode = "404", description = "Secuencia no encontrada", content = @Content)
    })
    @GetMapping("/{sequenceId}")
    public ResponseEntity<LearningSequence> consultar(@PathVariable Long sequenceId) {
        return consultarLearningSequenceUseCase.consultarPorId(sequenceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar secuencia", description = "Actualiza título/descripción de una secuencia existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secuencia actualizada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningSequence.class))),
            @ApiResponse(responseCode = "404", description = "Secuencia no encontrada", content = @Content)
    })
    @PutMapping("/{sequenceId}")
    public ResponseEntity<LearningSequence> actualizar(@PathVariable Long sequenceId,
                                                       @Valid @RequestBody ActualizarLearningSequenceDto request) {
        request.setId(sequenceId);
        return ResponseEntity.ok(actualizarLearningSequenceUseCase.actualizar(request));
    }

    @Operation(summary = "Eliminar secuencia", description = "Elimina (soft-delete) una secuencia existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Secuencia eliminada"),
            @ApiResponse(responseCode = "404", description = "Secuencia no encontrada", content = @Content)
    })
    @DeleteMapping("/{sequenceId}")
    public ResponseEntity<Void> eliminar(@PathVariable Long sequenceId) {
        eliminarLearningSequenceUseCase.eliminar(sequenceId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Publicar secuencia",
            description = "Congela una nueva LearningSequenceVersion y marca la secuencia como PUBLISHED. Exige al menos un item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secuencia publicada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningSequence.class))),
            @ApiResponse(responseCode = "400", description = "La secuencia no tiene items", content = @Content),
            @ApiResponse(responseCode = "404", description = "Secuencia no encontrada", content = @Content)
    })
    @PostMapping("/{sequenceId}/publish")
    public ResponseEntity<LearningSequence> publicar(@PathVariable Long sequenceId) {
        return ResponseEntity.ok(publicarLearningSequenceUseCase.publicar(sequenceId));
    }

    @Operation(summary = "Archivar secuencia", description = "Marca la secuencia como ARCHIVED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Secuencia archivada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningSequence.class)))
    })
    @PostMapping("/{sequenceId}/archive")
    public ResponseEntity<LearningSequence> archivar(@PathVariable Long sequenceId) {
        return ResponseEntity.ok(archivarLearningSequenceUseCase.archivar(sequenceId));
    }

    @Operation(summary = "Duplicar secuencia", description = "Crea una copia DRAFT de la secuencia, sus items y dependencias")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Secuencia duplicada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningSequence.class)))
    })
    @PostMapping("/{sequenceId}/duplicate")
    public ResponseEntity<LearningSequence> duplicar(@PathVariable Long sequenceId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(duplicarLearningSequenceUseCase.duplicar(sequenceId));
    }

    @Operation(summary = "Agregar item", description = "Agrega una actividad o un grupo al final del nivel indicado (raíz o dentro de un grupo)")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item agregado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LearningSequenceItem.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (p.ej. anidamiento de más de un nivel)", content = @Content),
            @ApiResponse(responseCode = "404", description = "Secuencia no encontrada", content = @Content)
    })
    @PostMapping("/{sequenceId}/items")
    public ResponseEntity<LearningSequenceItem> agregarItem(@PathVariable Long sequenceId,
                                                            @Valid @RequestBody AgregarItemSequenceDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agregarItemSequenceUseCase.agregarItem(sequenceId, request));
    }

    @Operation(summary = "Listar items", description = "Devuelve todos los items de la secuencia (raíz + anidados), ordenados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Items de la secuencia",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LearningSequenceItem.class))))
    })
    @GetMapping("/{sequenceId}/items")
    public ResponseEntity<List<LearningSequenceItem>> listarItems(@PathVariable Long sequenceId) {
        return ResponseEntity.ok(listarItemsSequenceUseCase.listarItems(sequenceId));
    }

    @Operation(summary = "Eliminar item", description = "Elimina un item (si es un grupo, también elimina su contenido)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item eliminado")
    })
    @DeleteMapping("/{sequenceId}/items/{itemId}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long sequenceId, @PathVariable Long itemId) {
        eliminarItemSequenceUseCase.eliminarItem(sequenceId, itemId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Reordenar items", description = "Actualiza el orden de los items de un mismo nivel (drag & drop)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Items reordenados",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LearningSequenceItem.class))))
    })
    @PutMapping("/{sequenceId}/items/reorder")
    public ResponseEntity<List<LearningSequenceItem>> reordenarItems(@PathVariable Long sequenceId,
                                                                     @Valid @RequestBody ReordenarItemsSequenceDto request) {
        return ResponseEntity.ok(reordenarItemsSequenceUseCase.reordenar(sequenceId, request));
    }

    @Operation(summary = "Definir dependencia", description = "Agrega una regla de desbloqueo: el item solo estará disponible si se cumple la regla sobre otro item de la misma secuencia")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dependencia creada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ActivityDependencyRule.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (p.ej. item de otra secuencia, o falta el valor de la regla)", content = @Content)
    })
    @PostMapping("/{sequenceId}/items/{itemId}/dependencies")
    public ResponseEntity<ActivityDependencyRule> definirDependencia(@PathVariable Long sequenceId,
                                                                     @PathVariable Long itemId,
                                                                     @Valid @RequestBody DefinirDependenciaDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(definirDependenciaUseCase.definir(sequenceId, itemId, request));
    }

    @Operation(summary = "Listar dependencias de un item", description = "Devuelve las reglas de desbloqueo definidas para el item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dependencias del item",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivityDependencyRule.class))))
    })
    @GetMapping("/{sequenceId}/items/{itemId}/dependencies")
    public ResponseEntity<List<ActivityDependencyRule>> listarDependencias(@PathVariable Long sequenceId, @PathVariable Long itemId) {
        return ResponseEntity.ok(listarDependenciasItemUseCase.listarDependencias(itemId));
    }

    @Operation(summary = "Eliminar dependencia", description = "Elimina una regla de desbloqueo del item")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Dependencia eliminada")
    })
    @DeleteMapping("/{sequenceId}/items/{itemId}/dependencies/{dependencyId}")
    public ResponseEntity<Void> eliminarDependencia(@PathVariable Long sequenceId, @PathVariable Long itemId,
                                                    @PathVariable Long dependencyId) {
        eliminarDependenciaUseCase.eliminarDependencia(itemId, dependencyId);
        return ResponseEntity.noContent().build();
    }
}
