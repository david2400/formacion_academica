package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_catalog;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackCriterion;

import java.util.List;

public interface ListarCriteriosFeedbackUseCase {
    List<FeedbackCriterion> listarCriterios();
}
