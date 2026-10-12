package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.acudiente.AcudienteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante.EstudianteRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarAcudientesPorEstudianteService implements ListarAcudientesPorEstudianteUseCase {

    private final AcudienteRepositoryPort acudienteRepositoryPort;
    private final EstudianteRepositoryPort estudianteRepositoryPort;

    @Override
    public List<Acudiente> listar(Long estudianteId) {
        validarExistenciaEstudiante(estudianteId);
        return acudienteRepositoryPort.listarPorEstudiante(estudianteId);
    }

    private void validarExistenciaEstudiante(Long estudianteId) {
        estudianteRepositoryPort.obtenerPorId(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
    }
}
