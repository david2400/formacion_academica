package com.kleverkids.formacion_academica.modules.control_academico.application.services.estudiante_examen;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.estudiante_examen.ListarEstudiantesExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.estudiante_examen.EstudianteExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarEstudiantesExamenService implements ListarEstudiantesExamenUseCase {

    private final EstudianteExamenRepositoryPort repositoryPort;

    @Override
    public List<EstudianteExamenDto> listar(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}
