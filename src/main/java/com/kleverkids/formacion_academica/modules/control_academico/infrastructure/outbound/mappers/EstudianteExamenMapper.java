package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.RegistrarEstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EstudianteExamenMapper {

    private EstudianteExamenMapper() {
    }

    public static EstudianteExamenEntity toEntity(RegistrarEstudianteExamenDto dto) {
        EstudianteExamenEntity entity = new EstudianteExamenEntity();
        entity.setId(UUID.randomUUID());
        entity.setExamenId(dto.examenId());
        entity.setEstudianteId(dto.estudianteId());
        entity.setAsignadoEn(LocalDateTime.now());
        entity.setRespuestas(new ArrayList<>());
        return entity;
    }

    public static EstudianteExamenDto toDto(EstudianteExamenEntity entity) {
        return new EstudianteExamenDto(
                entity.getId(),
                entity.getExamenId(),
                entity.getEstudianteId(),
                entity.getAsignadoEn()
        );
    }

    public static List<EstudianteExamenDto> toDtoList(List<EstudianteExamenEntity> entities) {
        return entities.stream().map(EstudianteExamenMapper::toDto).toList();
    }
}
