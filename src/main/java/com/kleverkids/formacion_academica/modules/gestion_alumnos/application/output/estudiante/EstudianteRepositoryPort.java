package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EstudianteRepositoryPort {

    EstudianteDto guardar(CrearEstudianteDto request);

    EstudianteDto actualizar(UpdateEstudianteDto request);

    Optional<EstudianteDto> obtenerPorId(UUID estudianteId);

    List<EstudianteDto> listar();

    boolean existePorDocumento(String tipoDocumento, String numeroDocumento);
}
