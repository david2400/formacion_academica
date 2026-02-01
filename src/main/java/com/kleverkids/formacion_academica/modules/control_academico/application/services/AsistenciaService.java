package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarHistorialAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.RegistrarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.asistencia.AsistenciaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AsistenciaService implements RegistrarAsistenciaUseCase, ConsultarHistorialAsistenciaUseCase {

    private final AsistenciaRepositoryPort asistenciaRepositoryPort;

    @Override
    public AsistenciaDto registrar(RegistrarAsistenciaDto request) {
        return asistenciaRepositoryPort.guardar(request);
    }

    @Override
    public HistorialAsistenciaDto consultar(HistorialAsistenciaFiltroDto filtro) {
        return asistenciaRepositoryPort.consultarHistorial(filtro);
    }
}
