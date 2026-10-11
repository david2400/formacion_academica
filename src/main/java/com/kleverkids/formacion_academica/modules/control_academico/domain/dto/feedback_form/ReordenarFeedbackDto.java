package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Nuevo orden completo de un nivel (secciones de un formulario o preguntas de una sección). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReordenarFeedbackDto {

    @NotEmpty(message = "Debes enviar el orden completo")
    private List<Long> ids;
}
