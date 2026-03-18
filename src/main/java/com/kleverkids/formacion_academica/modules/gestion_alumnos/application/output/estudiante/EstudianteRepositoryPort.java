package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Estudiante;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepositoryPort {

    Estudiante guardar(CrearEstudianteDto request);

    Estudiante actualizar(UpdateEstudianteDto request);

    Optional<Estudiante> obtenerPorId(Long estudianteId);

    List<Estudiante> listar();

    Page<Estudiante> listar(Pageable pageable);

    boolean existePorDocumento(String tipoDocumento, String numeroDocumento);

    void eliminar(Long estudianteId);
}
