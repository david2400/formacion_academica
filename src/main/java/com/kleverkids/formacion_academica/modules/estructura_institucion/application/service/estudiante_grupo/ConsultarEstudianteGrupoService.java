package com.kleverkids.formacion_academica.modules.estructura_institucion.application.service.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ConsultarEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConsultarEstudianteGrupoService implements ConsultarEstudianteGrupoUseCase {
    
    @Override
    public EstudianteGrupoDto consultarPorId(UUID estudianteGrupoId) {
        // TODO: Implementar la lógica de consulta de estudiante-grupo
        return new EstudianteGrupoDto(
            estudianteGrupoId,
            UUID.randomUUID(), // estudianteId - valor por defecto
            UUID.randomUUID(), // grupoId - valor por defecto
            java.time.LocalDate.now(), // fechaAsignacion - valor por defecto
            "ACTIVO" // estado - valor por defecto
        );
    }
}
