package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepositoryPort {

    EstudianteDto guardar(CrearEstudianteDto request);

    EstudianteDto actualizar(UpdateEstudianteDto request);

    Optional<EstudianteDto> obtenerPorId(Long estudianteId);

    List<EstudianteDto> listar();

    Page<EstudianteDto> listar(Pageable pageable);

    boolean existePorDocumento(String tipoDocumento, String numeroDocumento);

    void eliminar(Long estudianteId);
}
