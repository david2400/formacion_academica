package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.EstadoSolicitudFeedback;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject.VentanaFeedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Aplicación de un formulario PUBLICADO a clases reales.
 *
 * <p>Alcance: una clase ({@link #claseId}) o las clases de un grupo
 * ({@link #grupoId}) dentro de [{@link #desde}, {@link #hasta}] — exactamente
 * uno de los dos, como grupo/estudiante en LearningAssignment. Las
 * solicitudes por clase NO se materializan: se derivan de esta regla (evita
 * miles de filas y sincronizar con altas/cambios de clases).
 *
 * <p>Ciclo de vida en el motor ({@code FEEDBACK_APLICACION_LIFECYCLE}):
 * BORRADOR → ACTIVA → CERRADA, o CANCELADA.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAssignment {

    private Long id;
    private Long formId;
    private String nombre;

    private Long claseId;
    private Long grupoId;
    private LocalDate desde;
    private LocalDate hasta;

    /** Minutos después del fin de la clase en que abre la ventana (0 = al terminar). */
    private Integer aperturaOffsetMinutos;

    /** Horas que permanece abierta la ventana de cada clase. */
    private Integer vigenciaHoras;

    /** Límites absolutos opcionales que recortan la ventana de cada clase. */
    private LocalDateTime abreEn;
    private LocalDateTime cierraEn;

    /** Si true, solo pueden responder estudiantes con asistencia "presente". */
    private boolean soloAsistentes;

    /** Mínimo de respuestas para mostrar resultados/comentarios de un formulario anónimo. */
    private Integer umbralAnonimato;

    /** Réplica del estado en el motor (FEEDBACK_APLICACION_LIFECYCLE); el motor manda. */
    private Long estadoId;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Ventana efectiva para una clase concreta: abre al terminar la clase más
     * el offset y dura {@link #vigenciaHoras}; si hay límites absolutos, la
     * recortan (nunca la amplían).
     */
    public VentanaFeedback ventanaPara(LocalDateTime finClase) {
        int offset = aperturaOffsetMinutos == null ? 0 : aperturaOffsetMinutos;
        int horas = vigenciaHoras == null ? 72 : vigenciaHoras;

        LocalDateTime abre = finClase.plusMinutes(offset);
        LocalDateTime cierra = abre.plusHours(horas);

        if (abreEn != null && abreEn.isAfter(abre)) {
            abre = abreEn;
        }
        if (cierraEn != null && cierraEn.isBefore(cierra)) {
            cierra = cierraEn;
        }
        return new VentanaFeedback(abre, cierra);
    }

    /**
     * Estado derivado de la solicitud de un estudiante para una clase.
     *
     * @param aplicacionVigente false si la aplicación está CERRADA o CANCELADA en el motor
     * @param yaRespondio       true si existe participación y no se permiten respuestas múltiples
     */
    public EstadoSolicitudFeedback estadoSolicitud(LocalDateTime finClase, LocalDateTime ahora,
                                                   boolean aplicacionVigente, boolean yaRespondio) {
        if (yaRespondio) {
            return EstadoSolicitudFeedback.RESPONDIDO;
        }
        if (!aplicacionVigente) {
            return EstadoSolicitudFeedback.CERRADO;
        }
        VentanaFeedback ventana = ventanaPara(finClase);
        if (ventana.esVacia() || ahora.isAfter(ventana.cierra())) {
            return EstadoSolicitudFeedback.EXPIRADO;
        }
        if (ahora.isBefore(ventana.abre())) {
            return EstadoSolicitudFeedback.PENDIENTE;
        }
        return EstadoSolicitudFeedback.DISPONIBLE;
    }

    public boolean esPorClase() {
        return claseId != null;
    }
}
