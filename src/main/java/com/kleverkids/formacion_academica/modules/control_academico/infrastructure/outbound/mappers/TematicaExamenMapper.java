package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaExamenEntity;

import java.util.List;
import java.util.UUID;

public final class TematicaExamenMapper {

    private TematicaExamenMapper() {
    }

    public static TematicaExamenEntity toEntity(CrearTematicaExamenDto dto) {
        TematicaExamenEntity entity = new TematicaExamenEntity();
        entity.setId(UUID.randomUUID());
        entity.setExamenId(dto.examenId());
        entity.setTitulo(dto.titulo());
        entity.setDescripcion(dto.descripcion());
        entity.setOrden(dto.orden());
        return entity;
    }

    public static void applyUpdate(TematicaExamenEntity entity, ActualizarTematicaExamenDto dto) {
        if (dto.titulo() != null) {
            entity.setTitulo(dto.titulo());
        }
        if (dto.descripcion() != null) {
            entity.setDescripcion(dto.descripcion());
        }
        if (dto.orden() != null) {
            entity.setOrden(dto.orden());
        }
    }

    public static TematicaExamenDto toDto(TematicaExamenEntity entity) {
        return new TematicaExamenDto(
                entity.getId(),
                entity.getExamenId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getOrden()
        );
    }

    public static List<TematicaExamenDto> toDtoList(List<TematicaExamenEntity> entities) {
        return entities.stream().map(TematicaExamenMapper::toDto).toList();
    }
}
