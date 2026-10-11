package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_results.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackFormRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResultsQueryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResultsQueryPort.AgregadoPregunta;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResultsQueryPort.ConteoDistribucion;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResultsQueryPort.Totales;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Resultados y analítica. Toda agregación ocurre en SQL (FeedbackResultsQueryPort);
 * aquí solo se aplican reglas de presentación y de privacidad:
 *
 * <p><b>Umbral de anonimato (k):</b> si el filtro involucra formularios
 * anónimos y un agregado se apoya en menos de k respuestas, sus valores no se
 * devuelven ({@code datosSuficientes=false}). Con 1 o 2 respuestas un promedio
 * o un comentario identifica a quien respondió aunque no se guarde su id.
 */
@Service
@RequiredArgsConstructor
public class FeedbackResultsService implements ConsultarResumenFeedbackUseCase, ConsultarResultadosPreguntasFeedbackUseCase,
        ConsultarResultadosCriteriosFeedbackUseCase, ConsultarTendenciaFeedbackUseCase,
        ConsultarResultadosClasesFeedbackUseCase, ConsultarComentariosFeedbackUseCase {

    private static final BigDecimal CIEN = BigDecimal.valueOf(100);

    private final FeedbackResultsQueryPort resultados;
    private final FeedbackFormRepositoryPort formRepository;
    private final FeedbackAssignmentRepositoryPort assignmentRepository;
    private final FeedbackEstados estados;

    @Override
    @Transactional(readOnly = true)
    public ResumenResultadosFeedbackDto resumen(FiltroResultadosFeedbackDto filtro) {
        Totales totales = resultados.totales(filtro);
        long participaciones = resultados.participaciones(filtro);
        long elegibles = resultados.elegibles(filtro,
                estados.ids(FeedbackEstados.MAQUINA_APLICACION, FeedbackEstados.ACTIVA, FeedbackEstados.CERRADA),
                estados.ids(FeedbackEstados.MAQUINA_MEMBRESIA, FeedbackEstados.RETIRADO));
        int k = resultados.umbralAnonimato(filtro);
        boolean suficiente = suficiente(totales.respuestas(), k);

        BigDecimal pct = elegibles == 0 ? null : BigDecimal.valueOf(participaciones)
                .multiply(CIEN).divide(BigDecimal.valueOf(elegibles), 1, RoundingMode.HALF_UP)
                .min(CIEN);
        return ResumenResultadosFeedbackDto.builder()
                .respuestas(totales.respuestas())
                .participaciones(participaciones)
                .elegibles(elegibles)
                .participacionPct(pct)
                .indice(suficiente ? indice(totales.promedioNormalizado()) : null)
                .datosSuficientes(suficiente)
                .umbralAplicado(k)
                .build();
    }

    /**
     * Resultados por pregunta. Solo tienen sentido dentro de UN formulario
     * (cada versión tiene sus propias preguntas); para comparar entre
     * formularios está {@link #porCriterio}.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ResultadoPreguntaFeedbackDto> porPregunta(FiltroResultadosFeedbackDto filtro) {
        Long formId = resolverFormulario(filtro);
        FeedbackFormDetalleDto form = formRepository.detalle(formId)
                .orElseThrow(() -> new NotFoundException("Formulario de feedback no encontrado"));
        filtro.setFormId(formId);

        int k = resultados.umbralAnonimato(filtro);
        Map<Long, AgregadoPregunta> agregados = resultados.porPregunta(filtro).stream()
                .collect(Collectors.toMap(AgregadoPregunta::preguntaId, Function.identity()));
        Map<Long, List<ConteoDistribucion>> conteos = resultados.distribucion(filtro).stream()
                .collect(Collectors.groupingBy(ConteoDistribucion::preguntaId));

        List<ResultadoPreguntaFeedbackDto> salida = new ArrayList<>();
        for (FeedbackFormDetalleDto.PreguntaDetalle p : form.todasLasPreguntas()) {
            FeedbackQuestion q = p.getPregunta();
            AgregadoPregunta ag = agregados.get(q.getId());
            long n = ag == null ? 0 : ag.respuestas();
            boolean ok = suficiente(n, k);
            salida.add(ResultadoPreguntaFeedbackDto.builder()
                    .preguntaId(q.getId())
                    .seccionId(q.getSectionId())
                    .enunciado(q.getEnunciado())
                    .tipo(q.getTipo())
                    .criterionId(q.getCriterionId())
                    .respuestas(n)
                    .promedio(ok && ag != null && ag.promedio() != null ? ag.promedio().setScale(2, RoundingMode.HALF_UP) : null)
                    .indice(ok && ag != null ? indice(ag.promedioNormalizado()) : null)
                    .datosSuficientes(ok)
                    .distribucion(ok ? distribucion(p, conteos.getOrDefault(q.getId(), List.of())) : List.of())
                    .build());
        }
        return salida;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResultadoCriterioFeedbackDto> porCriterio(FiltroResultadosFeedbackDto filtro) {
        int k = resultados.umbralAnonimato(filtro);
        return resultados.porCriterio(filtro).stream()
                .map(c -> {
                    c.setIndice(suficiente(c.getRespuestas(), k) ? indice(c.getIndice()) : null);
                    return c;
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuntoTendenciaFeedbackDto> tendencia(FiltroResultadosFeedbackDto filtro, FeedbackResultsQueryPort.Granularidad granularidad) {
        int k = resultados.umbralAnonimato(filtro);
        return resultados.tendencia(filtro, granularidad == null ? FeedbackResultsQueryPort.Granularidad.MES : granularidad)
                .stream()
                .map(p -> {
                    boolean ok = suficiente(p.getRespuestas(), k);
                    p.setDatosSuficientes(ok);
                    p.setIndice(ok ? indice(p.getIndice()) : null);
                    return p;
                })
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResultadoClaseFeedbackDto> porClase(FiltroResultadosFeedbackDto filtro, Pageable pageable) {
        return resultados.porClase(filtro, pageable).map(f -> {
            ResultadoClaseFeedbackDto fila = f.fila();
            boolean ok = !f.anonimo() || fila.getRespuestas() >= f.umbral();
            fila.setDatosSuficientes(ok);
            fila.setIndice(ok ? indice(fila.getIndice()) : null);
            return fila;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ComentarioFeedbackDto> comentarios(FiltroResultadosFeedbackDto filtro, Pageable pageable) {
        return resultados.comentarios(filtro, pageable);
    }

    // ─── Apoyo ───────────────────────────────────────────────────────────────

    private Long resolverFormulario(FiltroResultadosFeedbackDto filtro) {
        if (filtro.getAssignmentId() != null) {
            FeedbackAssignment a = assignmentRepository.buscarPorId(filtro.getAssignmentId())
                    .orElseThrow(() -> new NotFoundException("Aplicación de feedback no encontrada"));
            if (filtro.getFormId() != null && !Objects.equals(filtro.getFormId(), a.getFormId())) {
                throw new IllegalArgumentException("La aplicación no corresponde al formulario indicado.");
            }
            return a.getFormId();
        }
        if (filtro.getFormId() == null) {
            throw new IllegalArgumentException("Selecciona un formulario o una aplicación para ver los resultados por pregunta.");
        }
        return filtro.getFormId();
    }

    /**
     * Distribución completa: incluye las opciones/valores con 0 respuestas
     * para que la gráfica muestre la escala entera y no solo lo elegido.
     */
    private static List<ItemDistribucionFeedbackDto> distribucion(FeedbackFormDetalleDto.PreguntaDetalle p, List<ConteoDistribucion> conteos) {
        FeedbackQuestion q = p.getPregunta();
        List<ItemDistribucionFeedbackDto> items = new ArrayList<>();
        switch (q.getTipo()) {
            case SINGLE_CHOICE, MULTIPLE_CHOICE -> {
                Map<Long, Long> porOpcion = new HashMap<>();
                conteos.forEach(c -> {
                    if (c.opcionId() != null) {
                        porOpcion.merge(c.opcionId(), c.cantidad(), Long::sum);
                    }
                });
                p.getOpciones().forEach(o -> items.add(ItemDistribucionFeedbackDto.builder()
                        .etiqueta(o.getEtiqueta()).valor(o.getValor()).opcionId(o.getId())
                        .cantidad(porOpcion.getOrDefault(o.getId(), 0L)).build()));
            }
            case LIKERT -> p.getNiveles().forEach(n -> items.add(ItemDistribucionFeedbackDto.builder()
                    .etiqueta(n.getEtiqueta()).valor(n.getValor())
                    .cantidad(contarValor(conteos, n.getValor())).build()));
            case NUMERIC_SCALE -> {
                for (int v = q.getEscalaMin(); v <= q.getEscalaMax(); v++) {
                    BigDecimal valor = BigDecimal.valueOf(v);
                    String etiqueta = String.valueOf(v);
                    if (v == q.getEscalaMin() && q.getEtiquetaMin() != null) {
                        etiqueta = v + " · " + q.getEtiquetaMin();
                    } else if (v == q.getEscalaMax() && q.getEtiquetaMax() != null) {
                        etiqueta = v + " · " + q.getEtiquetaMax();
                    }
                    items.add(ItemDistribucionFeedbackDto.builder()
                            .etiqueta(etiqueta).valor(valor).cantidad(contarValor(conteos, valor)).build());
                }
            }
            case YES_NO -> {
                items.add(ItemDistribucionFeedbackDto.builder().etiqueta("Sí").valor(BigDecimal.ONE)
                        .cantidad(contarValor(conteos, BigDecimal.ONE)).build());
                items.add(ItemDistribucionFeedbackDto.builder().etiqueta("No").valor(BigDecimal.ZERO)
                        .cantidad(contarValor(conteos, BigDecimal.ZERO)).build());
            }
            case OPEN_TEXT -> {
                // Sin distribución: se consultan en /comentarios.
            }
        }
        return items;
    }

    private static long contarValor(List<ConteoDistribucion> conteos, BigDecimal valor) {
        return conteos.stream()
                .filter(c -> c.opcionId() == null && c.valor() != null && c.valor().compareTo(valor) == 0)
                .mapToLong(ConteoDistribucion::cantidad)
                .sum();
    }

    private static boolean suficiente(long respuestas, int umbral) {
        return umbral <= 0 || respuestas >= umbral;
    }

    /** 0..1 → 0..100 con un decimal. */
    private static BigDecimal indice(BigDecimal normalizado) {
        return normalizado == null ? null : normalizado.multiply(CIEN).setScale(1, RoundingMode.HALF_UP);
    }
}
