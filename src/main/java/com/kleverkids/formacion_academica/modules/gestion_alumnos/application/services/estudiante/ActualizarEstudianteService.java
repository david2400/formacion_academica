package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ActualizarEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarEstudianteService implements ActualizarEstudianteUseCase {

    private final EstudianteRepositoryPort repositoryPort;
    private final EstudianteSoporte estudianteSoporte;

    @Override
    public Estudiante actualizar(UpdateEstudianteDto request) {
        // validarPersonaEmpresa(...) deshabilitada temporalmente: ver comentario
        // en crear().
        estudianteSoporte.aplicarPassword(request);
        return repositoryPort.actualizar(request);
    }
}
