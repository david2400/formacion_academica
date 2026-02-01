package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.estudiante_examen.ListarEstudiantesExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.estudiante_examen.RegistrarEstudianteExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.estudiante_examen.EstudianteExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.RegistrarEstudianteExamenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EstudianteExamenService implements RegistrarEstudianteExamenUseCase,
        ListarEstudiantesExamenUseCase {

    private final EstudianteExamenRepositoryPort repositoryPort;


    @Override
    public EstudianteExamenDto registrar(RegistrarEstudianteExamenDto request) {
        return repositoryPort.registrar(request);
    }

    @Override
    public List<EstudianteExamenDto> listar(UUID examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}
