package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public final class RelacionEstudianteAcudienteMapper {

    private RelacionEstudianteAcudienteMapper() {
    }

    public static EstudianteAcudienteEntity toEntity(CrearEstudianteAcudienteDto dto) {
        EstudianteAcudienteEntity entity = new EstudianteAcudienteEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteId(dto.estudianteId());
        entity.setAcudienteId(dto.acudienteId());
        entity.setParentesco(dto.parentesco());
        entity.setEsPrincipal(dto.esPrincipal());
        entity.setEstado("ACTIVA");
        entity.setFechaVinculacion(LocalDate.now());
        return entity;
    }

    public static void merge(EstudianteAcudienteEntity entity,
                             ActualizarEstudianteAcudienteDto dto) {
        if (dto.parentesco() != null) {
            entity.setParentesco(dto.parentesco());
        }
        if (dto.esPrincipal() != null) {
            entity.setEsPrincipal(dto.esPrincipal());
        }
        if (dto.estado() != null) {
            entity.setEstado(dto.estado());
            if ("INACTIVA".equalsIgnoreCase(dto.estado()) && entity.getFechaFin() == null) {
                entity.setFechaFin(LocalDate.now());
            }
        }
    }

    public static EstudianteAcudienteDto toDto(EstudianteAcudienteEntity entity) {
        return new EstudianteAcudienteDto(
                entity.getId(),
                entity.getEstudianteId(),
                entity.getAcudienteId(),
                entity.getParentesco(),
                entity.isEsPrincipal(),
                entity.getEstado(),
                entity.getFechaVinculacion(),
                entity.getFechaFin()
        );
    }

    public static List<EstudianteAcudienteDto> toDtoList(List<EstudianteAcudienteEntity> entities) {
        return entities.stream().map(RelacionEstudianteAcudienteMapper::toDto).toList();
    }
}
