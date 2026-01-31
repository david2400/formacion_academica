package com.kleverkids.formacion_academica.modules.control_academico.application.output.estudiante_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.RegistrarEstudianteExamenDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteExamenRepositoryPort {

    EstudianteExamenDto registrar(RegistrarEstudianteExamenDto request);

    List<EstudianteExamenDto> listarPorExamen(UUID examenId);

    Optional<EstudianteExamenDto> buscarPorExamenYEstudiante(UUID examenId, UUID estudianteId);
}
