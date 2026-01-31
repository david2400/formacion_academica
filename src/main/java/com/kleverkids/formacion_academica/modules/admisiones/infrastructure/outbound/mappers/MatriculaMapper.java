package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;

import java.util.List;
import java.util.UUID;

public final class MatriculaMapper {

    private MatriculaMapper() {
    }

    public static MatriculaEntity toEntity(CrearMatriculaDto dto) {
        MatriculaEntity entity = new MatriculaEntity();
        entity.setId(UUID.randomUUID());
        entity.setInscripcionId(dto.inscripcionId());
        entity.setEstudianteId(dto.estudianteId());
        entity.setGradoId(dto.gradoId());
        entity.setGrupoId(dto.grupoId());
        entity.setFechaMatricula(dto.fechaMatricula());
        entity.setRenovacion(dto.renovacion());
        entity.setEstado("ACTIVA");
        return entity;
    }

    public static MatriculaDto toDto(MatriculaEntity entity) {
        return new MatriculaDto(
                entity.getId(),
                entity.getInscripcionId(),
                entity.getEstudianteId(),
                entity.getGradoId(),
                entity.getGrupoId(),
                entity.getFechaMatricula(),
                entity.isRenovacion(),
                entity.getEstado(),
                entity.getObservaciones()
        );
    }

    public static List<MatriculaDto> toDtoList(List<MatriculaEntity> entities) {
        return entities.stream().map(MatriculaMapper::toDto).toList();
    }
}
