package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;

import java.util.UUID;

public final class GrupoMapper {

    private GrupoMapper() {
    }

    public static GrupoEntity toEntity(CrearGrupoDto dto) {
        GrupoEntity entity = new GrupoEntity();
        entity.setId(UUID.randomUUID());
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setGradoId(dto.gradoId());
        entity.setCapacidadMaxima(dto.capacidadMaxima());
        entity.setTutorId(dto.tutorId());
        entity.setAulaId(dto.aulaId());
        entity.setActivo(true);
        return entity;
    }

    public static void applyUpdate(GrupoEntity entity, ActualizarGrupoDto dto) {
        if (dto.nombre() != null) {
            entity.setNombre(dto.nombre());
        }
        if (dto.capacidadMaxima() != null) {
            entity.setCapacidadMaxima(dto.capacidadMaxima());
        }
        if (dto.tutorId() != null) {
            entity.setTutorId(dto.tutorId());
        }
        if (dto.aulaId() != null) {
            entity.setAulaId(dto.aulaId());
        }
        if (dto.activo() != null) {
            entity.setActivo(dto.activo());
        }
    }

    public static GrupoDto toDto(GrupoEntity entity) {
        return new GrupoDto(
                entity.getId(),
                entity.getCodigo(),
                entity.getNombre(),
                entity.getGradoId(),
                entity.getCapacidadMaxima(),
                entity.getTutorId(),
                entity.getAulaId(),
                entity.isActivo()
        );
    }
}
