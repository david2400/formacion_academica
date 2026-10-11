package com.kleverkids.formacion_academica.modules.control_academico.feedback.domain.model;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.EstadoSolicitudFeedback;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.VentanaFeedback;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reglas de ventana y estado derivado de una solicitud de feedback
 * (regla de negocio crítica: decide si un estudiante puede responder).
 */
class FeedbackAssignmentTest {

    private static final LocalDateTime FIN_CLASE = LocalDateTime.of(2026, 10, 15, 10, 0);

    private FeedbackAssignment aplicacion(int offsetMin, int vigenciaHoras) {
        return FeedbackAssignment.builder()
                .aperturaOffsetMinutos(offsetMin)
                .vigenciaHoras(vigenciaHoras)
                .build();
    }

    @Test
    void ventanaAbreAlTerminarLaClaseMasOffsetYDuraLaVigencia() {
        VentanaFeedback v = aplicacion(30, 48).ventanaPara(FIN_CLASE);

        assertThat(v.abre()).isEqualTo(FIN_CLASE.plusMinutes(30));
        assertThat(v.cierra()).isEqualTo(FIN_CLASE.plusMinutes(30).plusHours(48));
    }

    @Test
    void limitesAbsolutosRecortanPeroNoAmplianLaVentana() {
        FeedbackAssignment a = aplicacion(0, 72);
        a.setAbreEn(FIN_CLASE.plusHours(2));
        a.setCierraEn(FIN_CLASE.plusHours(24));

        VentanaFeedback v = a.ventanaPara(FIN_CLASE);

        assertThat(v.abre()).isEqualTo(FIN_CLASE.plusHours(2));
        assertThat(v.cierra()).isEqualTo(FIN_CLASE.plusHours(24));

        a.setAbreEn(FIN_CLASE.minusDays(5));
        a.setCierraEn(FIN_CLASE.plusDays(30));
        VentanaFeedback sinRecorte = a.ventanaPara(FIN_CLASE);
        assertThat(sinRecorte.abre()).isEqualTo(FIN_CLASE);
        assertThat(sinRecorte.cierra()).isEqualTo(FIN_CLASE.plusHours(72));
    }

    @Test
    void valoresPorDefectoSonAbrirAlTerminarYSetentaYDosHoras() {
        VentanaFeedback v = FeedbackAssignment.builder().build().ventanaPara(FIN_CLASE);

        assertThat(v.abre()).isEqualTo(FIN_CLASE);
        assertThat(v.cierra()).isEqualTo(FIN_CLASE.plusHours(72));
    }

    @Test
    void estadoDerivadoSegunElReloj() {
        FeedbackAssignment a = aplicacion(0, 24);

        assertThat(a.estadoSolicitud(FIN_CLASE, FIN_CLASE.minusMinutes(1), true, false))
                .isEqualTo(EstadoSolicitudFeedback.PENDIENTE);
        assertThat(a.estadoSolicitud(FIN_CLASE, FIN_CLASE.plusHours(1), true, false))
                .isEqualTo(EstadoSolicitudFeedback.DISPONIBLE);
        assertThat(a.estadoSolicitud(FIN_CLASE, FIN_CLASE.plusHours(25), true, false))
                .isEqualTo(EstadoSolicitudFeedback.EXPIRADO);
    }

    @Test
    void respondidoTienePrioridadYAplicacionCerradaBloquea() {
        FeedbackAssignment a = aplicacion(0, 24);
        LocalDateTime dentro = FIN_CLASE.plusHours(1);

        assertThat(a.estadoSolicitud(FIN_CLASE, dentro, false, true))
                .isEqualTo(EstadoSolicitudFeedback.RESPONDIDO);
        assertThat(a.estadoSolicitud(FIN_CLASE, dentro, false, false))
                .isEqualTo(EstadoSolicitudFeedback.CERRADO);
    }

    @Test
    void ventanaVaciaPorLimitesAbsolutosSeConsideraExpirada() {
        FeedbackAssignment a = aplicacion(0, 24);
        a.setCierraEn(FIN_CLASE.minusHours(1));

        assertThat(a.ventanaPara(FIN_CLASE).esVacia()).isTrue();
        assertThat(a.estadoSolicitud(FIN_CLASE, FIN_CLASE.minusHours(2), true, false))
                .isEqualTo(EstadoSolicitudFeedback.EXPIRADO);
    }
}
