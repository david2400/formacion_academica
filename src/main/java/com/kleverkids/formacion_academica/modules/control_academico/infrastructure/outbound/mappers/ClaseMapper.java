package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;

import java.util.UUID;

public final class ClaseMapper {

    private ClaseMapper() {
    }

    public static ClaseEntity toEntity(CrearClaseDto dto) {
        ClaseEntity entity = new ClaseEntity();
        entity.setId(UUID.randomUUID());
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setFechaInicio(dto.fechaInicio());
        entity.setFechaFin(dto.fechaFin());
        entity.setProfesoresIds(dto.profesoresIds());
        return entity;
    }

    public static ClaseDto toDto(ClaseEntity entity) {
        return new ClaseDto(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getProfesoresIds()
        );
    }
}
