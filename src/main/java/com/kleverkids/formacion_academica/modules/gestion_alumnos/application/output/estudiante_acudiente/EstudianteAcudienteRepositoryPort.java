package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante_acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;

import java.util.List;
import java.util.Optional;

public interface EstudianteAcudienteRepositoryPort {

    EstudianteAcudiente crear(CrearEstudianteAcudienteDto request);

    EstudianteAcudiente actualizar(ActualizarEstudianteAcudienteDto request);

    Optional<EstudianteAcudiente> obtenerPorId(Long relacionId);

    List<EstudianteAcudiente> listarPorEstudiante(Long estudianteId);

    List<EstudianteAcudiente> listarPorAcudiente(Long acudienteId);

    boolean existeRelacionPrincipal(Long estudianteId, Long excluirRelacionId);

    void eliminar(Long relacionId);
}
