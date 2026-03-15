package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;

import java.util.List;
import java.util.Optional;

public interface EstudianteAcudienteRepositoryPort {

    EstudianteAcudienteDto crear(CrearEstudianteAcudienteDto request);

    EstudianteAcudienteDto actualizar(ActualizarEstudianteAcudienteDto request);

    Optional<EstudianteAcudienteDto> obtenerPorId(Long relacionId);

    List<EstudianteAcudienteDto> listarPorEstudiante(Long estudianteId);

    List<EstudianteAcudienteDto> listarPorAcudiente(Long acudienteId);

    boolean existeRelacionPrincipal(Long estudianteId, Long excluirRelacionId);

    void eliminar(Long relacionId);
}
