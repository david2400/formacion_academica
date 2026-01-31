package com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;

public interface ConsultarHistorialAsistenciaUseCase {

    HistorialAsistenciaDto consultar(HistorialAsistenciaFiltroDto filtro);
}
