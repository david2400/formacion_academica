package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.AsistenciaEntity;

import java.util.List;
import java.util.UUID;

public final class AsistenciaMapper {

    private AsistenciaMapper() {
    }

    public static AsistenciaEntity toEntity(RegistrarAsistenciaDto dto) {
        AsistenciaEntity entity = new AsistenciaEntity();
        entity.setId(UUID.randomUUID());
        entity.setClaseId(dto.claseId());
        entity.setEstudianteId(dto.estudianteId());
        entity.setFechaRegistro(dto.fechaRegistro());
        entity.setPresente(dto.presente());
        return entity;
    }

    public static AsistenciaDto toDto(AsistenciaEntity entity) {
        return new AsistenciaDto(
                entity.getId(),
                entity.getClaseId(),
                entity.getEstudianteId(),
                entity.getFechaRegistro(),
                entity.isPresente()
        );
    }

    public static List<AsistenciaDto> toDtoList(List<AsistenciaEntity> entities) {
        return entities.stream().map(AsistenciaMapper::toDto).toList();
    }
}
