package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Respuesta del estudiante a una pregunta. Según el tipo se usa:
 * valorNumerico (NUMERIC_SCALE, LIKERT, YES_NO 1/0), opcionId (SINGLE_CHOICE),
 * opcionesIds (MULTIPLE_CHOICE) o texto (OPEN_TEXT).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaPreguntaFeedbackDto {

    @NotNull(message = "Cada respuesta debe indicar la pregunta")
    private Long preguntaId;

    private BigDecimal valorNumerico;
    private Long opcionId;

    @Builder.Default
    private List<Long> opcionesIds = new ArrayList<>();

    @Size(max = 4000, message = "El comentario no puede superar 4000 caracteres")
    private String texto;
}
