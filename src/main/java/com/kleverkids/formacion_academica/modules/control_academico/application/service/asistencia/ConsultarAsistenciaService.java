package com.kleverkids.formacion_academica.modules.control_academico.application.service.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ConsultarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConsultarAsistenciaService implements ConsultarAsistenciaUseCase {
    
    @Override
    public AsistenciaDto consultarPorId(Long asistenciaId) {
        // TODO: Implementar la lógica de consulta de asistencia
        return new AsistenciaDto(
            asistenciaId,
            1L, // claseId - valor por defecto
            1L, // estudianteId - valor por defecto
            LocalDateTime.now(),
            true // presente - valor por defecto
        );
    }
}
