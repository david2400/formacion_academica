package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteAcudienteRepositoryPort {

    EstudianteAcudienteDto crear(CrearEstudianteAcudienteDto request);

    EstudianteAcudienteDto actualizar(ActualizarEstudianteAcudienteDto request);

    Optional<EstudianteAcudienteDto> obtenerPorId(UUID relacionId);

    List<EstudianteAcudienteDto> listarPorEstudiante(UUID estudianteId);

    List<EstudianteAcudienteDto> listarPorAcudiente(UUID acudienteId);

    boolean existeRelacionPrincipal(UUID estudianteId, UUID excluirRelacionId);

    void eliminar(UUID relacionId);
}
