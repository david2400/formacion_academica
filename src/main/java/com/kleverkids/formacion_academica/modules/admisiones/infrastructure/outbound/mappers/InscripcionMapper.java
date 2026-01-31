package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;

import java.util.List;
import java.util.UUID;

public final class InscripcionMapper {

    private InscripcionMapper() {
    }

    public static InscripcionEntity toEntity(CrearInscripcionDto dto) {
        InscripcionEntity entity = new InscripcionEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteId(dto.estudianteId());
        entity.setPeriodoAcademico(dto.periodoAcademico());
        entity.setFechaSolicitud(dto.fechaSolicitud());
        entity.setEstado("PENDIENTE");
        entity.setObservaciones(dto.observaciones());
        return entity;
    }

    public static void applyEstado(InscripcionEntity entity, ActualizarEstadoInscripcionDto dto) {
        entity.setEstado(dto.nuevoEstado());
        entity.setObservaciones(dto.motivo());
    }

    public static InscripcionDto toDto(InscripcionEntity entity) {
        return new InscripcionDto(
                entity.getId(),
                entity.getEstudianteId(),
                entity.getPeriodoAcademico(),
                entity.getFechaSolicitud(),
                entity.getEstado(),
                entity.getObservaciones()
        );
    }

    public static List<InscripcionDto> toDtoList(List<InscripcionEntity> entities) {
        return entities.stream().map(InscripcionMapper::toDto).toList();
    }
}
