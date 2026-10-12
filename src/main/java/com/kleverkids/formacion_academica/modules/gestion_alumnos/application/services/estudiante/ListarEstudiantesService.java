package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarEstudiantesService implements ListarEstudiantesUseCase {

    private final EstudianteRepositoryPort repositoryPort;

    @Override
    public List<Estudiante> listar() {
        return repositoryPort.listar();
    }
}
