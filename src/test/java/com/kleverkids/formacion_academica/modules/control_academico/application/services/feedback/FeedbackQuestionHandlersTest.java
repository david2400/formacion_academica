package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** Reglas por tipo de pregunta: configuración válida, respuesta válida y normalización 0..1. */
class FeedbackQuestionHandlersTest {

    private static FeedbackQuestion pregunta() {
        return FeedbackQuestion.builder().id(10L).build();
    }

    private static RespuestaPreguntaFeedbackDto valor(String v) {
        return RespuestaPreguntaFeedbackDto.builder().preguntaId(10L).valorNumerico(new BigDecimal(v)).build();
    }

    @Test
    void escalaNumericaExigeRangoValidoYNormaliza() {
        NumericScaleQuestionHandler h = new NumericScaleQuestionHandler();
        FeedbackQuestion q = pregunta();
        q.setEscalaMin(0);
        q.setEscalaMax(10);
        DefinicionPreguntaFeedback d = new DefinicionPreguntaFeedback(q, null, null);

        h.validarDefinicion(d);
        List<FeedbackAnswer> filas = h.convertir(d, valor("7"));
        assertThat(filas.get(0).getValorNormalizado()).isEqualByComparingTo("0.7");

        assertThatThrownBy(() -> h.convertir(d, valor("11"))).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> h.convertir(d, valor("7.5"))).isInstanceOf(IllegalArgumentException.class);

        q.setEscalaMax(0);
        assertThatThrownBy(() -> h.validarDefinicion(d)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void likertSoloAceptaNivelesDeLaEscala() {
        LikertQuestionHandler h = new LikertQuestionHandler();
        FeedbackQuestion q = pregunta();
        q.setScaleId(1L);
        List<FeedbackScaleLevel> niveles = List.of(1, 2, 3, 4, 5).stream()
                .map(v -> FeedbackScaleLevel.builder().valor(BigDecimal.valueOf(v)).etiqueta("N" + v).build())
                .toList();
        DefinicionPreguntaFeedback d = new DefinicionPreguntaFeedback(q, null, niveles);

        h.validarDefinicion(d);
        assertThat(h.convertir(d, valor("5")).get(0).getValorNormalizado()).isEqualByComparingTo("1");
        assertThat(h.convertir(d, valor("1")).get(0).getValorNormalizado()).isEqualByComparingTo("0");
        assertThat(h.convertir(d, valor("4")).get(0).getValorNormalizado()).isEqualByComparingTo("0.75");
        assertThatThrownBy(() -> h.convertir(d, valor("6"))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void seleccionUnicaNoPermiteMezclarOpcionesConYSinValor() {
        SingleChoiceQuestionHandler h = new SingleChoiceQuestionHandler();
        DefinicionPreguntaFeedback mezcla = new DefinicionPreguntaFeedback(pregunta(), List.of(
                FeedbackQuestionOption.builder().id(1L).etiqueta("A").valor(BigDecimal.ONE).build(),
                FeedbackQuestionOption.builder().id(2L).etiqueta("B").build()), null);
        assertThatThrownBy(() -> h.validarDefinicion(mezcla)).isInstanceOf(IllegalArgumentException.class);

        DefinicionPreguntaFeedback repetidas = new DefinicionPreguntaFeedback(pregunta(), List.of(
                FeedbackQuestionOption.builder().id(1L).etiqueta("Sí").build(),
                FeedbackQuestionOption.builder().id(2L).etiqueta(" sí ").build()), null);
        assertThatThrownBy(() -> h.validarDefinicion(repetidas)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void seleccionMultipleRechazaOpcionesAjenasYGeneraUnaFilaPorOpcion() {
        MultipleChoiceQuestionHandler h = new MultipleChoiceQuestionHandler();
        DefinicionPreguntaFeedback d = new DefinicionPreguntaFeedback(pregunta(), List.of(
                FeedbackQuestionOption.builder().id(1L).etiqueta("A").build(),
                FeedbackQuestionOption.builder().id(2L).etiqueta("B").build(),
                FeedbackQuestionOption.builder().id(3L).etiqueta("C").build()), null);

        List<FeedbackAnswer> filas = h.convertir(d, RespuestaPreguntaFeedbackDto.builder()
                .preguntaId(10L).opcionesIds(List.of(1L, 3L, 3L)).build());
        assertThat(filas).extracting(FeedbackAnswer::getOptionId).containsExactly(1L, 3L);

        assertThatThrownBy(() -> h.convertir(d, RespuestaPreguntaFeedbackDto.builder()
                .preguntaId(10L).opcionesIds(List.of(99L)).build())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void siNoSoloAceptaCeroOUnoYTextoSeRecorta() {
        YesNoQuestionHandler sn = new YesNoQuestionHandler();
        DefinicionPreguntaFeedback d = new DefinicionPreguntaFeedback(pregunta(), null, null);
        assertThat(sn.convertir(d, valor("1")).get(0).getValorNormalizado()).isEqualByComparingTo("1");
        assertThatThrownBy(() -> sn.convertir(d, valor("2"))).isInstanceOf(IllegalArgumentException.class);

        OpenTextQuestionHandler texto = new OpenTextQuestionHandler();
        assertThat(texto.respondida(RespuestaPreguntaFeedbackDto.builder().texto("   ").build())).isFalse();
        assertThat(texto.convertir(d, RespuestaPreguntaFeedbackDto.builder().texto("  Muy clara  ").build())
                .get(0).getTexto()).isEqualTo("Muy clara");
    }
}
