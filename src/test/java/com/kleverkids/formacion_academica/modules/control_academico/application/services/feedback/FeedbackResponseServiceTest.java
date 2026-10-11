package com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.ContextoAcademicoFeedbackPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.ContextoAcademicoFeedbackPort.Candidata;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackFormRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResponseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.feedback.handler.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.FeedbackFormDetalleDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.RespuestaPreguntaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_response.ResponderFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.FeedbackValidacionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject.FeedbackQuestionType;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackParticipation;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackResponse;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Reglas críticas al responder: ventana, una respuesta por estudiante,
 * obligatorias validadas en backend y anonimato real.
 */
class FeedbackResponseServiceTest {

    private static final ZoneId ZONA = ZoneId.of("America/Bogota");
    private static final LocalDateTime FIN_CLASE = LocalDateTime.of(2026, 10, 15, 10, 0);
    private static final Long EST = 7L, APLICACION = 3L, CLASE = 20L, FORM = 5L, ACTIVA = 101L;

    private ContextoAcademicoFeedbackPort contexto;
    private FeedbackAssignmentRepositoryPort aplicaciones;
    private FeedbackFormRepositoryPort formularios;
    private FeedbackResponseRepositoryPort respuestas;
    private FeedbackEstados estados;
    private FeedbackResponseService service;

    private FeedbackForm form;

    @BeforeEach
    void setUp() {
        contexto = mock(ContextoAcademicoFeedbackPort.class);
        aplicaciones = mock(FeedbackAssignmentRepositoryPort.class);
        formularios = mock(FeedbackFormRepositoryPort.class);
        respuestas = mock(FeedbackResponseRepositoryPort.class);
        estados = mock(FeedbackEstados.class);
        FeedbackQuestionTypeHandlerRegistry registry = new FeedbackQuestionTypeHandlerRegistry(Map.of(
                "LIKERT", new LikertQuestionHandler(),
                "OPEN_TEXT", new OpenTextQuestionHandler(),
                "YES_NO", new YesNoQuestionHandler()));
        service = new FeedbackResponseService(contexto, aplicaciones, formularios, respuestas, registry,
                mock(FeedbackEventPublisher.class), estados);

        form = FeedbackForm.builder().id(FORM).titulo("Clase").anonimo(true).build();
        FeedbackQuestion obligatoria = FeedbackQuestion.builder().id(1L).formId(FORM).sectionId(9L)
                .tipo(FeedbackQuestionType.YES_NO).enunciado("¿Se cumplió el objetivo?").obligatoria(true).build();
        FeedbackQuestion comentario = FeedbackQuestion.builder().id(2L).formId(FORM).sectionId(9L)
                .tipo(FeedbackQuestionType.OPEN_TEXT).enunciado("Comentarios").obligatoria(false).build();
        FeedbackFormDetalleDto detalle = FeedbackFormDetalleDto.builder()
                .form(form)
                .secciones(List.of(FeedbackFormDetalleDto.SeccionDetalle.builder()
                        .seccion(FeedbackSection.builder().id(9L).build())
                        .preguntas(List.of(
                                FeedbackFormDetalleDto.PreguntaDetalle.builder().pregunta(obligatoria).build(),
                                FeedbackFormDetalleDto.PreguntaDetalle.builder().pregunta(comentario).build()))
                        .build()))
                .build();

        when(contexto.candidata(eq(EST), eq(APLICACION), eq(CLASE), anyList()))
                .thenReturn(Optional.of(new Candidata(APLICACION, CLASE, "Inglés A1", FIN_CLASE.minusHours(2), FIN_CLASE, 4L)));
        when(aplicaciones.buscarPorId(APLICACION)).thenReturn(Optional.of(FeedbackAssignment.builder()
                .id(APLICACION).formId(FORM).aperturaOffsetMinutos(0).vigenciaHoras(48).estadoId(ACTIVA).build()));
        when(formularios.detalle(FORM)).thenReturn(Optional.of(detalle));
        when(estados.ids(eq(FeedbackEstados.MAQUINA_APLICACION), any(String[].class))).thenReturn(List.of(ACTIVA));
        when(estados.es(FeedbackEstados.MAQUINA_APLICACION, ACTIVA, FeedbackEstados.ACTIVA)).thenReturn(true);
        relojEn(FIN_CLASE.plusHours(3).plusMinutes(17));
    }

    private void relojEn(LocalDateTime instante) {
        service.setClock(Clock.fixed(instante.atZone(ZONA).toInstant(), ZONA));
    }

    private static ResponderFeedbackDto envio(RespuestaPreguntaFeedbackDto... r) {
        return ResponderFeedbackDto.builder().respuestas(List.of(r)).build();
    }

    private static RespuestaPreguntaFeedbackDto si() {
        return RespuestaPreguntaFeedbackDto.builder().preguntaId(1L).valorNumerico(BigDecimal.ONE).build();
    }

    @Test
    void respuestaAnonimaNoGuardaEstudianteNiHoraExacta() {
        service.responder(EST, APLICACION, CLASE, envio(si()));

        ArgumentCaptor<FeedbackResponse> respuesta = ArgumentCaptor.forClass(FeedbackResponse.class);
        ArgumentCaptor<FeedbackParticipation> participacion = ArgumentCaptor.forClass(FeedbackParticipation.class);
        verify(respuestas).registrar(respuesta.capture(), participacion.capture());

        assertThat(respuesta.getValue().getEstudianteId()).isNull();
        assertThat(respuesta.getValue().getEnviadoEn()).isEqualTo(FIN_CLASE.plusHours(3));
        assertThat(participacion.getValue().getEstudianteId()).isEqualTo(EST);
        assertThat(participacion.getValue().getNumero()).isEqualTo(1);
    }

    @Test
    void respuestaIdentificadaConservaEstudiante() {
        form.setAnonimo(false);
        service.responder(EST, APLICACION, CLASE, envio(si()));

        ArgumentCaptor<FeedbackResponse> respuesta = ArgumentCaptor.forClass(FeedbackResponse.class);
        verify(respuestas).registrar(respuesta.capture(), any());
        assertThat(respuesta.getValue().getEstudianteId()).isEqualTo(EST);
    }

    @Test
    void obligatoriaSinResponderDevuelveErrorPorPregunta() {
        RespuestaPreguntaFeedbackDto soloComentario = RespuestaPreguntaFeedbackDto.builder().preguntaId(2L).texto("Bien").build();

        assertThatThrownBy(() -> service.responder(EST, APLICACION, CLASE, envio(soloComentario)))
                .isInstanceOf(FeedbackValidacionException.class)
                .satisfies(e -> assertThat(((FeedbackValidacionException) e).getErrores()).containsOnlyKeys(1L));
        verify(respuestas, never()).registrar(any(), any());
    }

    @Test
    void noSePuedeResponderDosVecesNiFueraDeLaVentana() {
        when(respuestas.contarParticipaciones(APLICACION, CLASE, EST)).thenReturn(1L);
        assertThatThrownBy(() -> service.responder(EST, APLICACION, CLASE, envio(si())))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("Ya respondiste");

        when(respuestas.contarParticipaciones(APLICACION, CLASE, EST)).thenReturn(0L);
        relojEn(FIN_CLASE.plusHours(49));
        assertThatThrownBy(() -> service.responder(EST, APLICACION, CLASE, envio(si())))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("plazo");

        relojEn(FIN_CLASE.minusMinutes(5));
        assertThatThrownBy(() -> service.responder(EST, APLICACION, CLASE, envio(si())))
                .isInstanceOf(IllegalStateException.class).hasMessageContaining("aún no está disponible");
        verify(respuestas, never()).registrar(any(), any());
    }

    @Test
    void estudianteNoElegibleNoVeLaSolicitud() {
        when(contexto.candidata(eq(99L), any(), any(), anyList())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.responder(99L, APLICACION, CLASE, envio(si())))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void respuestasAPreguntasAjenasSeRechazan() {
        RespuestaPreguntaFeedbackDto ajena = RespuestaPreguntaFeedbackDto.builder().preguntaId(999L).texto("x").build();
        assertThatThrownBy(() -> service.responder(EST, APLICACION, CLASE, envio(si(), ajena)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
