package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;

import java.util.List;
import java.util.UUID;

public final class EstudianteGrupoMapper {

    private EstudianteGrupoMapper() {
    }

    public static EstudianteGrupoEntity toEntity(AsignarEstudianteGrupoDto dto) {
        EstudianteGrupoEntity entity = new EstudianteGrupoEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteId(dto.estudianteId());
        entity.setGrupoId(dto.grupoId());
        entity.setFechaAsignacion(dto.fechaAsignacion());
        entity.setEstado("ASIGNADO");
        return entity;
    }

    public static EstudianteGrupoDto toDto(EstudianteGrupoEntity entity) {
        return new EstudianteGrupoDto(
                entity.getId(),
                entity.getEstudianteId(),
                entity.getGrupoId(),
                entity.getFechaAsignacion(),
                entity.getEstado()
        );
    }

    public static List<EstudianteGrupoDto> toDtoList(List<EstudianteGrupoEntity> entities) {
        return entities.stream().map(EstudianteGrupoMapper::toDto).toList();
    }
}
