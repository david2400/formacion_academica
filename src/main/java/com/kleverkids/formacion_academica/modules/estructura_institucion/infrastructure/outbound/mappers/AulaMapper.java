package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;

import java.util.List;
import java.util.UUID;

public final class AulaMapper {

    private AulaMapper() {
    }

    public static AulaEntity toEntity(CrearAulaDto dto) {
        AulaEntity entity = new AulaEntity();
        entity.setId(UUID.randomUUID());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setCapacidad(dto.capacidad());
        entity.setActivo(dto.activo() != null ? dto.activo() : true);
        return entity;
    }

    public static AulaDto toDto(AulaEntity entity) {
        return new AulaDto(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getCapacidad(),
                entity.isActivo()
        );
    }

    public static List<AulaDto> toDtoList(List<AulaEntity> entities) {
        return entities.stream().map(AulaMapper::toDto).toList();
    }

    public static void applyUpdate(AulaEntity entity, ActualizarAulaDto dto) {
        if (dto.nombre() != null && !dto.nombre().isBlank()) {
            entity.setNombre(dto.nombre());
        }
        if (dto.descripcion() != null) {
            entity.setDescripcion(dto.descripcion());
        }
        if (dto.capacidad() != null) {
            entity.setCapacidad(dto.capacidad());
        }
        if (dto.activo() != null) {
            entity.setActivo(dto.activo());
        }
    }
}
