package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ConsultarEstudianteAcudienteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarEstudianteAcudienteService implements ConsultarEstudianteAcudienteUseCase {

    private final EstudianteAcudienteRepositoryPort relacionRepositoryPort;

    @Override
    public EstudianteAcudiente consultarPorId(Long relacionId) {
        return relacionRepositoryPort.obtenerPorId(relacionId)
                .orElseThrow(() -> new IllegalArgumentException("Relación estudiante-acudiente no encontrada"));
    }
}
