package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;

import java.util.UUID;

public final class GradoMapper {

    private GradoMapper() {
    }

    public static GradoEntity toEntity(CrearGradoDto dto) {
        GradoEntity entity = new GradoEntity();
        entity.setId(UUID.randomUUID());
        entity.setNombre(dto.nombre());
        entity.setNivelEducativo(dto.nivelEducativo());
        entity.setOrden(dto.orden());
        return entity;
    }

    public static GradoDto toDto(GradoEntity entity) {
        return new GradoDto(
                entity.getId(),
                entity.getNombre(),
                entity.getNivelEducativo(),
                entity.getOrden()
        );
    }
}
