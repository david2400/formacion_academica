package com.kleverkids.formacion_academica.modules.control_academico.application.services.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.RegistrarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.asistencia.AsistenciaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrarAsistenciaService implements RegistrarAsistenciaUseCase {

    private final AsistenciaRepositoryPort asistenciaRepositoryPort;

    @Override
    public AsistenciaDto registrar(RegistrarAsistenciaDto request) {
        return asistenciaRepositoryPort.guardar(request);
    }
}
