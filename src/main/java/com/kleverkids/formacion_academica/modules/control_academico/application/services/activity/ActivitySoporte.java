package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Lógica compartida por varios casos de uso de activity (antes métodos privados de
 * ActivityService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class ActivitySoporte {

    private final ActivityRepositoryPort activityRepositoryPort;

    Activity obtenerOFallar(Long id) {
        return activityRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
    }
}
