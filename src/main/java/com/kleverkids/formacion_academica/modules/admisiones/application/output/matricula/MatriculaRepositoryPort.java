package com.kleverkids.formacion_academica.modules.admisiones.application.output.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepositoryPort {

    Matricula registrar(CrearMatriculaDto request);

    Optional<Matricula> obtenerPorId(Long matriculaId);

    List<Matricula> listarPorEstudiante(Long estudianteId);

    void eliminar(Long matriculaId);
}
