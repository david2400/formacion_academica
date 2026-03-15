package com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepositoryPort {

    MatriculaDto registrar(CrearMatriculaDto request);

    Optional<MatriculaDto> obtenerPorId(Long matriculaId);

    List<MatriculaDto> listarPorEstudiante(Long estudianteId);

    void eliminar(Long matriculaId);
}
