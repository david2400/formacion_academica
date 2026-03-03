package com.kleverkids.formacion_academica.modules.admisiones.application.service.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula.ActualizarMatriculaUseCase;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import org.springframework.stereotype.Service;

@Service
public class ActualizarMatriculaService implements ActualizarMatriculaUseCase {
    
    @Override
    public MatriculaDto actualizar(ActualizarMatriculaDto request) {
        // TODO: Implementar la lógica de actualización de matrícula
        // Por ahora, retornamos un DTO básico para que la aplicación inicie
        return new MatriculaDto(
            request.matriculaId(),
            null, // inscripcionId - no está en ActualizarMatriculaDto
            request.estudianteId(),
            request.gradoId(),
            request.grupoId(),
            request.fechaMatricula(),
            false, // renovacion - valor por defecto
            request.estado(),
            request.observaciones()
        );
    }
}
