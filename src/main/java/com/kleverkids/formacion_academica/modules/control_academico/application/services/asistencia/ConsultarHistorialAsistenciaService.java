package com.kleverkids.formacion_academica.modules.control_academico.application.services.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarHistorialAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.asistencia.AsistenciaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarHistorialAsistenciaService implements ConsultarHistorialAsistenciaUseCase {

    private final AsistenciaRepositoryPort asistenciaRepositoryPort;

    @Override
    public HistorialAsistenciaDto consultar(HistorialAsistenciaFiltroDto filtro) {
        return asistenciaRepositoryPort.consultarHistorial(filtro);
    }
}
