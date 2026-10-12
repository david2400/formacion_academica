package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante.ListarEstudiantesPaginadoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListarEstudiantesPaginadoService implements ListarEstudiantesPaginadoUseCase {

    private final EstudianteRepositoryPort repositoryPort;

    @Override
    public Page<Estudiante> listar(Pageable pageable) {
        return repositoryPort.listar(pageable);
    }
}
