package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Formulario completo listo para pintar (builder del coordinador o formulario
 * del estudiante): secciones ordenadas, preguntas ordenadas, opciones y
 * niveles de escala ya resueltos. Se arma con 4 consultas en total, no N+1.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackFormDetalleDto {

    private FeedbackForm form;

    @Builder.Default
    private List<SeccionDetalle> secciones = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeccionDetalle {
        private FeedbackSection seccion;

        @Builder.Default
        private List<PreguntaDetalle> preguntas = new ArrayList<>();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreguntaDetalle {
        private FeedbackQuestion pregunta;

        @Builder.Default
        private List<FeedbackQuestionOption> opciones = new ArrayList<>();

        /** Niveles de la escala (solo LIKERT). */
        @Builder.Default
        private List<FeedbackScaleLevel> niveles = new ArrayList<>();
    }

    /** Todas las preguntas, en orden de presentación. */
    public List<PreguntaDetalle> todasLasPreguntas() {
        List<PreguntaDetalle> todas = new ArrayList<>();
        secciones.forEach(s -> todas.addAll(s.getPreguntas()));
        return todas;
    }
}
