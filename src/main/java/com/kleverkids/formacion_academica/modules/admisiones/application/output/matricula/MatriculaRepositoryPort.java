package com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatriculaRepositoryPort {

    MatriculaDto registrar(CrearMatriculaDto request);

    Optional<MatriculaDto> obtenerPorId(UUID matriculaId);

    List<MatriculaDto> listarPorEstudiante(UUID estudianteId);

    void eliminar(UUID matriculaId);
}
