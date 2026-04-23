package com.kleverkids.formacion_academica.modules.control_academico.application.input.asignacion_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.AsignacionExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.BuscarAsignacionesDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuscarAsignacionesExamenUseCase {
    Page<AsignacionExamenDto> buscar(BuscarAsignacionesDto criterios, Pageable pageable);
}
