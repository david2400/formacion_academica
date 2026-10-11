package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Envío completo de un feedback (v1 no guarda borradores en servidor). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponderFeedbackDto {

    @Valid
    @NotNull
    @Builder.Default
    private List<RespuestaPreguntaFeedbackDto> respuestas = new ArrayList<>();
}
