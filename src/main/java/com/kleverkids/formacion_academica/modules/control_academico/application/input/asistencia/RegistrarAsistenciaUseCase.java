package com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;

public interface RegistrarAsistenciaUseCase {

    AsistenciaDto registrar(RegistrarAsistenciaDto request);
}
