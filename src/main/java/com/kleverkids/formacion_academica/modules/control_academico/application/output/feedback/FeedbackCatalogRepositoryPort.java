package com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackScaleDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackCriterion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScale;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;

import java.util.List;
import java.util.Optional;

/** Catálogos reutilizables: escalas (con niveles) y criterios. */
public interface FeedbackCatalogRepositoryPort {

    List<FeedbackScaleDetalleDto> listarEscalas();

    Optional<FeedbackScaleDetalleDto> buscarEscala(Long scaleId);

    /** Guarda la escala y reemplaza por completo sus niveles. */
    FeedbackScaleDetalleDto guardarEscala(FeedbackScale escala, List<FeedbackScaleLevel> niveles);

    /** true si alguna pregunta de un formulario NO borrador usa la escala (sus niveles ya no se pueden cambiar). */
    boolean escalaEnUsoPorFormulariosNoEditables(Long scaleId, List<Long> estadosEditables);

    List<FeedbackCriterion> listarCriterios();

    Optional<FeedbackCriterion> buscarCriterio(Long criterionId);

    Optional<FeedbackCriterion> buscarCriterioPorCodigo(String codigo);

    FeedbackCriterion guardarCriterio(FeedbackCriterion criterio);
}
