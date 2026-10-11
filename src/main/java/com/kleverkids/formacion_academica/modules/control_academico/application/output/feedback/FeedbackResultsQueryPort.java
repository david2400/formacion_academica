package com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ComentarioFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.FiltroResultadosFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.PuntoTendenciaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ResultadoClaseFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ResultadoCriterioFeedbackDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

/**
 * Consultas agregadas de resultados. Todo se calcula en la base de datos
 * (AVG/COUNT/GROUP BY); nunca se traen respuestas individuales para sumarlas
 * en Java ni en el frontend.
 */
public interface FeedbackResultsQueryPort {

    /** Respuestas y promedio normalizado (0..1) del filtro. */
    Totales totales(FiltroResultadosFeedbackDto filtro);

    long participaciones(FiltroResultadosFeedbackDto filtro);

    /**
     * Estudiantes elegibles sumados sobre las clases ya terminadas del alcance
     * de aplicaciones en los estados dados (las que efectivamente recolectaron).
     */
    long elegibles(FiltroResultadosFeedbackDto filtro, List<Long> estadosAplicacion, List<Long> estadosMembresiaExcluidos);

    /** Mayor umbral de anonimato entre las aplicaciones de formularios anónimos del filtro (0 si no hay). */
    int umbralAnonimato(FiltroResultadosFeedbackDto filtro);

    List<AgregadoPregunta> porPregunta(FiltroResultadosFeedbackDto filtro);

    /** Conteos por (pregunta, opción) y (pregunta, valor). Las etiquetas las pone el servicio. */
    List<ConteoDistribucion> distribucion(FiltroResultadosFeedbackDto filtro);

    List<ResultadoCriterioFeedbackDto> porCriterio(FiltroResultadosFeedbackDto filtro);

    List<PuntoTendenciaFeedbackDto> tendencia(FiltroResultadosFeedbackDto filtro, Granularidad granularidad);

    /** Ranking por (aplicación, clase); cada fila trae su propio umbral para ocultar lo que no lo alcanza. */
    Page<FilaClase> porClase(FiltroResultadosFeedbackDto filtro, Pageable pageable);

    /** Comentarios abiertos; excluye los de clases anónimas que no alcanzan su umbral. */
    Page<ComentarioFeedbackDto> comentarios(FiltroResultadosFeedbackDto filtro, Pageable pageable);

    enum Granularidad { SEMANA, MES }

    record Totales(long respuestas, BigDecimal promedioNormalizado) {
    }

    record AgregadoPregunta(Long preguntaId, long respuestas, BigDecimal promedio, BigDecimal promedioNormalizado) {
    }

    record ConteoDistribucion(Long preguntaId, Long opcionId, BigDecimal valor, long cantidad) {
    }

    record FilaClase(ResultadoClaseFeedbackDto fila, boolean anonimo, int umbral) {
    }
}
