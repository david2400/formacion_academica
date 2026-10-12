package com.kleverkids.formacion_academica.modules.admisiones.application.services.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ListarMatriculasUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula.MatriculaRepositoryPort;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarMatriculasService implements ListarMatriculasUseCase {

    private final MatriculaRepositoryPort matriculaRepositoryPort;

    @Override
    public List<Matricula> listarPorEstudiante(Long estudianteId) {
        return matriculaRepositoryPort.listarPorEstudiante(estudianteId);
    }
}
