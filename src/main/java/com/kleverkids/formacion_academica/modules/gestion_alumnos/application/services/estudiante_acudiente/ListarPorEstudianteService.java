package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante_acudiente.ListarPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente.EstudianteAcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarPorEstudianteService implements ListarPorEstudianteUseCase {

    private final EstudianteAcudienteRepositoryPort relacionRepositoryPort;
    private final EstudianteAcudienteSoporte estudianteAcudienteSoporte;

    @Override
    public List<EstudianteAcudiente> listarPorEstudiante(Long estudianteId) {
        estudianteAcudienteSoporte.validarExistenciaEstudiante(estudianteId);
        return relacionRepositoryPort.listarPorEstudiante(estudianteId);
    }
}
