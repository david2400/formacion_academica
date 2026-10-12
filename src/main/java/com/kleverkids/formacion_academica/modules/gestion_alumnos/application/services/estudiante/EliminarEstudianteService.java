package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ConsultarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.EliminarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarEstudianteService implements EliminarEstudianteUseCase {

    private final EstudianteRepositoryPort repositoryPort;
    private final ConsultarEstudianteUseCase consultarEstudianteUseCase;

    @Override
    public void eliminar(Long estudianteId) {
        consultarEstudianteUseCase.consultarPorId(estudianteId);
        repositoryPort.eliminar(estudianteId);
    }
}
