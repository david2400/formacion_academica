package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.InscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    InscripcionDto toDto(InscripcionEntity entity);

    List<InscripcionDto> toDtoList(List<InscripcionEntity> entities);

    default InscripcionEntity toEntity(CrearInscripcionDto dto) {
        InscripcionEntity entity = new InscripcionEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteId(dto.getEstudianteId());
        entity.setPeriodoAcademico(dto.getPeriodoAcademico());
        entity.setFechaSolicitud(dto.getFechaSolicitud());
        entity.setEstado("PENDIENTE");
        entity.setObservaciones(dto.getObservaciones());
        return entity;
    }

    default void applyEstado(InscripcionEntity entity, ActualizarEstadoInscripcionDto dto) {
    }
}
