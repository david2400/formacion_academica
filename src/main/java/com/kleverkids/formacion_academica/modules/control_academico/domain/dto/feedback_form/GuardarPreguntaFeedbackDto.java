package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject.FeedbackQuestionType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Alta/edición de una pregunta. Qué campos aplican depende de {@code tipo}; la
 * coherencia la valida el handler del tipo, no anotaciones (que no saben de
 * tipos).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuardarPreguntaFeedbackDto {

    @NotNull(message = "La sección es obligatoria")
    private Long sectionId;

    @NotNull(message = "El tipo de pregunta es obligatorio")
    private FeedbackQuestionType tipo;

    @NotBlank(message = "El enunciado es obligatorio")
    @Size(max = 500)
    private String enunciado;

    @Size(max = 500)
    private String ayuda;

    private boolean obligatoria;

    private Long scaleId;
    private Long criterionId;
    private Integer escalaMin;
    private Integer escalaMax;

    @Size(max = 100)
    private String etiquetaMin;

    @Size(max = 100)
    private String etiquetaMax;

    /** Solo SINGLE_CHOICE / MULTIPLE_CHOICE. Reemplaza las opciones existentes. */
    @Valid
    @Builder.Default
    private List<OpcionPreguntaFeedbackDto> opciones = new ArrayList<>();
}
