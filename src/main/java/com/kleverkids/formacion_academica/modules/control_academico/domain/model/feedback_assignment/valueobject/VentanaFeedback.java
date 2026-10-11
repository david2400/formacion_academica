package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject;

import java.time.LocalDateTime;

/**
 * Ventana efectiva [abre, cierra] en la que un estudiante puede responder el
 * feedback de una clase concreta.
 */
public record VentanaFeedback(LocalDateTime abre, LocalDateTime cierra) {

    public boolean contiene(LocalDateTime instante) {
        return !instante.isBefore(abre) && !instante.isAfter(cierra);
    }

    /** Una ventana puede quedar vacía si los límites absolutos la recortan por completo. */
    public boolean esVacia() {
        return cierra.isBefore(abre);
    }
}
