package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FiltroFeedbackFormDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarFeedbackFormsUseCase {
    Page<FeedbackForm> listar(FiltroFeedbackFormDto filtro, Pageable pageable);
}
