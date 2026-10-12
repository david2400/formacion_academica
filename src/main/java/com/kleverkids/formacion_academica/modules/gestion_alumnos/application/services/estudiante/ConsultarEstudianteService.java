package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarEstudianteService implements ConsultarEstudianteUseCase {

    private final EstudianteRepositoryPort repositoryPort;

    @Override
    public Estudiante consultarPorId(Long estudianteId) {
        return repositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }

    @Override
    public Estudiante consultarPorTipoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
        return repositoryPort.obtenerPorTipoYNumeroDocumento(tipoDocumento, numeroDocumento)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }
}
