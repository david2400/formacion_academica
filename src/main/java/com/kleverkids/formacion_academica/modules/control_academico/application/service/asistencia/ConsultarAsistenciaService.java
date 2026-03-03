package com.kleverkids.formacion_academica.modules.control_academico.application.service.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConsultarAsistenciaService implements ConsultarAsistenciaUseCase {
    
    @Override
    public AsistenciaDto consultarPorId(UUID asistenciaId) {
        // TODO: Implementar la lógica de consulta de asistencia
        return new AsistenciaDto(
            asistenciaId,
            UUID.randomUUID(), // claseId - valor por defecto
            UUID.randomUUID(), // estudianteId - valor por defecto
            LocalDateTime.now(),
            true // presente - valor por defecto
        );
    }
}
