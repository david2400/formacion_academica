package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Lógica compartida por varios casos de uso de estudiante_acudiente (antes métodos privados de
 * EstudianteAcudienteService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class EstudianteAcudienteSoporte {

    private final EstudianteRepositoryPort estudianteRepositoryPort;
    private final AcudienteRepositoryPort acudienteRepositoryPort;

    void validarExistenciaEstudiante(Long estudianteId) {
        estudianteRepositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    void validarExistenciaAcudiente(Long acudienteId) {
        acudienteRepositoryPort.obtenerPorId(acudienteId)
                .orElseThrow(() -> new IllegalArgumentException("Acudiente no encontrado"));
    }
}
