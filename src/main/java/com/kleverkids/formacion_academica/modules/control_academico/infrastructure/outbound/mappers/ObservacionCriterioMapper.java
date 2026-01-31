package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ObservacionCriterioEntity;

import java.util.List;
import java.util.UUID;

public final class ObservacionCriterioMapper {

    private ObservacionCriterioMapper() {
    }

    public static ObservacionCriterioEntity toEntity(RegistrarObservacionCriterioDto dto) {
        ObservacionCriterioEntity entity = new ObservacionCriterioEntity();
        entity.setId(UUID.randomUUID());
        entity.setExamenId(dto.examenId());
        entity.setCriterioId(dto.criterioId());
        entity.setEstudianteId(dto.estudianteId());
        entity.setPuntaje(dto.puntaje());
        entity.setObservacion(dto.observacion());
        entity.setRecomendacion(dto.recomendacion());
        return entity;
    }

    public static void applyUpdate(ObservacionCriterioEntity entity, ActualizarObservacionCriterioDto dto) {
        if (dto.puntaje() != null) {
            entity.setPuntaje(dto.puntaje());
        }
        if (dto.observacion() != null) {
            entity.setObservacion(dto.observacion());
        }
        if (dto.recomendacion() != null) {
            entity.setRecomendacion(dto.recomendacion());
        }
    }

    public static ObservacionCriterioDto toDto(ObservacionCriterioEntity entity) {
        return new ObservacionCriterioDto(
                entity.getId(),
                entity.getExamenId(),
                entity.getCriterioId(),
                entity.getEstudianteId(),
                entity.getPuntaje(),
                entity.getObservacion(),
                entity.getRecomendacion()
        );
    }

    public static List<ObservacionCriterioDto> toDtoList(List<ObservacionCriterioEntity> entities) {
        return entities.stream().map(ObservacionCriterioMapper::toDto).toList();
    }
}
