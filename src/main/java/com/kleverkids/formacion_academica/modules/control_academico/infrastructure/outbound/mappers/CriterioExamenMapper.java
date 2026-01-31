package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CriterioExamenEntity;

import java.util.List;
import java.util.UUID;

public final class CriterioExamenMapper {

    private CriterioExamenMapper() {
    }

    public static CriterioExamenEntity toEntity(CrearCriterioExamenDto dto) {
        CriterioExamenEntity entity = new CriterioExamenEntity();
        entity.setId(UUID.randomUUID());
        entity.setExamenId(dto.examenId());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setPonderacion(dto.ponderacion());
        entity.setOrden(dto.orden());
        entity.setRecomendacionBase(dto.recomendacionBase());
        return entity;
    }

    public static void applyUpdate(CriterioExamenEntity entity, ActualizarCriterioExamenDto dto) {
        if (dto.nombre() != null) {
            entity.setNombre(dto.nombre());
        }
        if (dto.descripcion() != null) {
            entity.setDescripcion(dto.descripcion());
        }
        if (dto.ponderacion() != null) {
            entity.setPonderacion(dto.ponderacion());
        }
        if (dto.orden() != null) {
            entity.setOrden(dto.orden());
        }
        if (dto.recomendacionBase() != null) {
            entity.setRecomendacionBase(dto.recomendacionBase());
        }
    }

    public static CriterioExamenDto toDto(CriterioExamenEntity entity) {
        return new CriterioExamenDto(
                entity.getId(),
                entity.getExamenId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPonderacion(),
                entity.getOrden(),
                entity.getRecomendacionBase()
        );
    }

    public static List<CriterioExamenDto> toDtoList(List<CriterioExamenEntity> entities) {
        return entities.stream().map(CriterioExamenMapper::toDto).toList();
    }
}
