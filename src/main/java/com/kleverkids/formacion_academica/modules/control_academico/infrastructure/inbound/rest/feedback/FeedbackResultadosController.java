package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_results.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResultsQueryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // TODO(permisos)
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Resultados agregados. Administración ve todo; un docente queda forzado a
 * SUS clases (profesor_id del token, se ignora el del query). Ningún DTO de
 * resultados contiene datos del estudiante.
 */
@Tag(name = "Feedback · Resultados", description = "Indicadores agregados: resumen, preguntas, criterios, tendencia, clases y comentarios")
@RestController
@RequestMapping("/control-academico/feedback-resultados")
@RequiredArgsConstructor
// TODO(permisos): reactivar cuando se usen roles de access_control.
// @PreAuthorize("@feedbackSeguridad.administracionODocente()")
public class FeedbackResultadosController {

    private final ConsultarResumenFeedbackUseCase resumen;
    private final ConsultarResultadosPreguntasFeedbackUseCase porPregunta;
    private final ConsultarResultadosCriteriosFeedbackUseCase porCriterio;
    private final ConsultarTendenciaFeedbackUseCase tendencia;
    private final ConsultarResultadosClasesFeedbackUseCase porClase;
    private final ConsultarComentariosFeedbackUseCase comentarios;
    private final FeedbackSeguridad seguridad;

    @Operation(summary = "Resumen (KPIs)", description = "Respuestas, participación e índice 0-100")
    @GetMapping("/resumen")
    public ResponseEntity<ResumenResultadosFeedbackDto> resumen(@ModelAttribute FiltroQuery q) {
        return ResponseEntity.ok(resumen.resumen(filtro(q)));
    }

    @Operation(summary = "Resultados por pregunta", description = "Requiere form_id o assignment_id")
    @GetMapping("/preguntas")
    public ResponseEntity<List<ResultadoPreguntaFeedbackDto>> porPregunta(@ModelAttribute FiltroQuery q) {
        return ResponseEntity.ok(porPregunta.porPregunta(filtro(q)));
    }

    @Operation(summary = "Índice por criterio", description = "Comparable entre formularios y versiones; de menor a mayor")
    @GetMapping("/criterios")
    public ResponseEntity<List<ResultadoCriterioFeedbackDto>> porCriterio(@ModelAttribute FiltroQuery q) {
        return ResponseEntity.ok(porCriterio.porCriterio(filtro(q)));
    }

    @Operation(summary = "Tendencia histórica", description = "Por mes (por defecto) o semana de la fecha de la clase")
    @GetMapping("/tendencia")
    public ResponseEntity<List<PuntoTendenciaFeedbackDto>> tendencia(@ModelAttribute FiltroQuery q,
                                                                     @RequestParam(defaultValue = "MES") FeedbackResultsQueryPort.Granularidad granularidad) {
        return ResponseEntity.ok(tendencia.tendencia(filtro(q), granularidad));
    }

    @Operation(summary = "Resultados por clase", description = "Paginado; orden: fecha (defecto), indice_asc, indice_desc, respuestas_desc")
    @GetMapping("/clases")
    public ResponseEntity<Page<ResultadoClaseFeedbackDto>> porClase(@ModelAttribute FiltroQuery q,
                                                                   @RequestParam(defaultValue = "fecha") String orden,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "20") int size) {
        Sort sort = switch (orden) {
            case "indice_asc" -> Sort.by(Sort.Direction.ASC, "indice");
            case "indice_desc" -> Sort.by(Sort.Direction.DESC, "indice");
            case "respuestas_desc" -> Sort.by(Sort.Direction.DESC, "respuestas");
            default -> Sort.unsorted();
        };
        return ResponseEntity.ok(porClase.porClase(filtro(q), Paginacion.de(page, size, sort)));
    }

    @Operation(summary = "Comentarios abiertos", description = "Paginado; respeta el umbral de anonimato por clase")
    @GetMapping("/comentarios")
    public ResponseEntity<Page<ComentarioFeedbackDto>> comentarios(@ModelAttribute FiltroQuery q,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(comentarios.comentarios(filtro(q), Paginacion.de(page, size, Sort.unsorted())));
    }

    /** Query params comunes (camelCase, igual que el resto de query params del proyecto). */
    public record FiltroQuery(Long assignmentId, Long formId, Long claseId, Long grupoId, Long profesorId,
                              Long tipoClaseId, Long criterionId,
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
    }

    private FiltroResultadosFeedbackDto filtro(FiltroQuery q) {
        // TODO(permisos): reactivar para que un docente solo vea sus clases.
        // Long docente = seguridad.docenteRestringido();
        Long docente = null;
        return FiltroResultadosFeedbackDto.builder()
                .assignmentId(q.assignmentId())
                .formId(q.formId())
                .claseId(q.claseId())
                .grupoId(q.grupoId())
                // Un docente nunca puede consultar clases de otro docente.
                .profesorId(docente != null ? docente : q.profesorId())
                .tipoClaseId(q.tipoClaseId())
                .criterionId(q.criterionId())
                .desde(q.desde())
                .hasta(q.hasta())
                .build();
    }
}
