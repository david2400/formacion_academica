package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScale;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Escala con sus niveles ordenados. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackScaleDetalleDto {

    private FeedbackScale escala;

    @Builder.Default
    private List<FeedbackScaleLevel> niveles = new ArrayList<>();
}
