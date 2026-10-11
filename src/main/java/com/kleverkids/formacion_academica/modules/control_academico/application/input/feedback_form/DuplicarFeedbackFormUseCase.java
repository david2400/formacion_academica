package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;

/** Crea una nueva versión (BORRADOR) a partir de cualquier formulario, incluido uno publicado. */
public interface DuplicarFeedbackFormUseCase {
    FeedbackForm duplicar(Long formId);
}
