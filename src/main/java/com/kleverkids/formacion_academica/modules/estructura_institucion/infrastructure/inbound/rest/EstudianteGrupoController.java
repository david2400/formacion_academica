//package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.inbound.rest;
//
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.AsignarEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.CambiarEstadoEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ConsultarEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.EliminarEstudianteGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudiantesPorGrupoUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudianteGruposUseCase;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
//import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.context.annotation.Description;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

//import java.util.List;
//
// @Description(value = "Gestiona la asignación de estudiantes a grupos")
// @Tag(name = "Estudiantes por Grupo", description = "Gestiona la asignación de estudiantes a grupos")
// @RestController
// @RequestMapping("/estructura-institucion/estudiantes-grupo")
// public class EstudianteGrupoController {
//
//     private final AsignarEstudianteGrupoUseCase asignarUseCase;
//     private final CambiarEstadoEstudianteGrupoUseCase cambiarEstadoUseCase;
//     private final ConsultarEstudianteGrupoUseCase consultarUseCase;
//     private final EliminarEstudianteGrupoUseCase eliminarUseCase;
//     private final ListarEstudiantesPorGrupoUseCase listarPorGrupoUseCase;
//     private final ListarEstudianteGruposUseCase listarTodosUseCase;
//
//     public EstudianteGrupoController(AsignarEstudianteGrupoUseCase asignarUseCase,
//                                      CambiarEstadoEstudianteGrupoUseCase cambiarEstadoUseCase,
//                                      ConsultarEstudianteGrupoUseCase consultarUseCase,
//                                      EliminarEstudianteGrupoUseCase eliminarUseCase,
//                                      ListarEstudiantesPorGrupoUseCase listarPorGrupoUseCase,
//                                      ListarEstudianteGruposUseCase listarTodosUseCase) {
//         this.asignarUseCase = asignarUseCase;
//         this.cambiarEstadoUseCase = cambiarEstadoUseCase;
//         this.consultarUseCase = consultarUseCase;
//         this.eliminarUseCase = eliminarUseCase;
//         this.listarPorGrupoUseCase = listarPorGrupoUseCase;
//         this.listarTodosUseCase = listarTodosUseCase;
//     }
//
//     @Operation(summary = "Asignar estudiante a grupo", description = "Crea una nueva asignación de estudiante a grupo")
//     @ApiResponses({
//             @ApiResponse(responseCode = "201", description = "Asignación creada",
//                     content = @Content(mediaType = "application/json",
//                             schema = @Schema(implementation = EstudianteGrupo.class))),
//             @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
//             @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
//     })
//     @PostMapping
//     public ResponseEntity<EstudianteGrupo> asignar(@RequestBody AsignarEstudianteGrupoDto request) {
//         return ResponseEntity.status(HttpStatus.CREATED).body(asignarUseCase.asignar(request));
//     }
//
//     @Operation(summary = "Consultar asignación", description = "Obtiene los datos de una asignación específica")
//     @ApiResponses({
//             @ApiResponse(responseCode = "200", description = "Asignación encontrada",
//                     content = @Content(mediaType = "application/json",
//                             schema = @Schema(implementation = EstudianteGrupo.class))),
//             @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
//             @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
//     })
//     @GetMapping("/{estudianteGrupoId}")
//     public ResponseEntity<EstudianteGrupo> consultar(@PathVariable Long estudianteGrupoId) {
//         return ResponseEntity.ok(consultarUseCase.consultarPorId(estudianteGrupoId));
//     }
//
//     @Operation(summary = "Listar asignaciones", description = "Obtiene todas las asignaciones registradas")
//     @ApiResponses({
//             @ApiResponse(responseCode = "200", description = "Listado de asignaciones",
//                     content = @Content(mediaType = "application/json",
//                             array = @ArraySchema(schema = @Schema(implementation = EstudianteGrupo.class))))),
//             @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
//     })
//     @GetMapping
//     public ResponseEntity<List<EstudianteGrupo>> listar() {
//         return ResponseEntity.ok(listarTodosUseCase.listar());
//     }
//
//     @Operation(summary = "Listar por grupo", description = "Obtiene las asignaciones de un grupo específico")
//     @ApiResponses({
//             @ApiResponse(responseCode = "200", description = "Asignaciones del grupo",
//                     content = @Content(mediaType = "application/json",
//                             array = @ArraySchema(schema = @Schema(implementation = EstudianteGrupo.class))))),
//             @ApiResponse(responseCode = "404", description = "Grupo no encontrado", content = @Content),
//             @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
//     })
//     @GetMapping("/grupo/{grupoId}")
//     public ResponseEntity<List<EstudianteGrupo>> listarPorGrupo(@PathVariable Long grupoId) {
//         return ResponseEntity.ok(listarPorGrupoUseCase.listar(grupoId));
//     }
//
//     @Operation(summary = "Cambiar estado", description = "Actualiza el estado de una asignación de estudiante a grupo")
//     @ApiResponses({
//             @ApiResponse(responseCode = "200", description = "Estado actualizado",
//                     content = @Content(mediaType = "application/json",
//                             schema = @Schema(implementation = EstudianteGrupo.class))),
//             @ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
//             @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
//             @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
//     })
//     @PutMapping("/estado")
//     public ResponseEntity<EstudianteGrupo> cambiarEstado(@RequestBody CambiarEstadoEstudianteGrupoDto request) {
//         return ResponseEntity.ok(cambiarEstadoUseCase.cambiarEstado(request));
//     }
//
//     @Operation(summary = "Eliminar asignación", description = "Elimina la asignación de un estudiante a un grupo")
//     @ApiResponses({
//             @ApiResponse(responseCode = "200", description = "Asignación eliminada", content = @Content),
//             @ApiResponse(responseCode = "404", description = "Asignación no encontrada", content = @Content),
//             @ApiResponse(responseCode = "500", description = "Error interno", content = @Content)
//     })
//     @DeleteMapping("/{estudianteGrupoId}")
//     public ResponseEntity<Void> eliminar(@PathVariable Long estudianteGrupoId) {
//         eliminarUseCase.eliminar(estudianteGrupoId);
//         return ResponseEntity.noContent().build();
//     }
// }
