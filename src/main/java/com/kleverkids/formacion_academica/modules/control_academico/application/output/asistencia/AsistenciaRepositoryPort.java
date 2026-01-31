package com.kleverkids.formacion_academica.modules.control_academico.application.output.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;

public interface AsistenciaRepositoryPort {

    AsistenciaDto guardar(RegistrarAsistenciaDto request);

    HistorialAsistenciaDto consultarHistorial(HistorialAsistenciaFiltroDto filtro);
}
