package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Filtros del listado de formularios. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroFeedbackFormDto {

    private String texto;
    private Long estadoId;
}
