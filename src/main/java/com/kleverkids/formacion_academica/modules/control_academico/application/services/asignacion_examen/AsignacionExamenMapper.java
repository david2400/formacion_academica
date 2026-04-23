package com.kleverkids.formacion_academica.modules.control_academico.application.services.asignacion_examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.AsignacionExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.CrearAsignacionExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen.AsignacionExamenEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AsignacionExamenMapper {
    
    public AsignacionExamenDto toDto(AsignacionExamenEntity entity, String examenNombre, String claseNombre) {
        if (entity == null) return null;
        
        return new AsignacionExamenDto(
            entity.getId(),
            entity.getExamenId(),
            examenNombre,
            entity.getClaseId(),
            claseNombre,
            entity.getGrado(),
            entity.getGrupo(),
            entity.getFechaAsignacion(),
            entity.getFechaInicio(),
            entity.getFechaFin(),
            entity.getDuracionMinutos(),
            entity.getIntentosPermitidos(),
            entity.getMostrarResultadosInmediatos(),
            entity.getPermitirRevision(),
            entity.getEstado(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }
    
    public AsignacionExamenEntity toEntity(CrearAsignacionExamenDto dto, String grado, String grupo) {
        return AsignacionExamenEntity.builder()
            .examenId(dto.examenId())
            .claseId(dto.claseId())
            .grado(grado)
            .grupo(grupo)
            .fechaAsignacion(dto.fechaAsignacion())
            .fechaInicio(dto.fechaInicio())
            .fechaFin(dto.fechaFin())
            .duracionMinutos(dto.duracionMinutos())
            .intentosPermitidos(dto.intentosPermitidos())
            .mostrarResultadosInmediatos(dto.mostrarResultadosInmediatos())
            .permitirRevision(dto.permitirRevision())
            .estado("PROGRAMADA")
            .build();
    }
}
