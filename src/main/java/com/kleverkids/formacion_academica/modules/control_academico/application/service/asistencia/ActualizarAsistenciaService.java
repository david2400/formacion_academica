package com.kleverkids.formacion_academica.modules.control_academico.application.service.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ActualizarAsistenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.ActualizarAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class ActualizarAsistenciaService implements ActualizarAsistenciaUseCase {
    
    @Override
    public AsistenciaDto actualizar(ActualizarAsistenciaDto request) {
        // TODO: Implementar la lógica de actualización de asistencia
        // Por ahora, convertimos el estado a booleano presente
        boolean presente = "PRESENTE".equalsIgnoreCase(request.estado()) || "ASISTIO".equalsIgnoreCase(request.estado());
        return new AsistenciaDto(
            request.asistenciaId(),
            request.claseId(),
            request.estudianteId(),
            java.time.LocalDateTime.now(), // fechaRegistro - valor por defecto
            presente
        );
    }
}
