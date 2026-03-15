package com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;

public interface ConsultarAsistenciaUseCase {
    AsistenciaDto consultarPorId(Long asistenciaId);
}
