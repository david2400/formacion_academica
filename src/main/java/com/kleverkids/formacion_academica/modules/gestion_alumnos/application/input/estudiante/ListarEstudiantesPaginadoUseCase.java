package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarEstudiantesPaginadoUseCase {

    Page<EstudianteDto> listar(Pageable pageable);
}
