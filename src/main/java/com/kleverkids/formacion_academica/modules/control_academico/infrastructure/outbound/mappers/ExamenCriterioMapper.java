package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ActualizarExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.CrearExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ExamenCriterioEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamenCriterioMapper {

    ExamenCriterioEntity toEntity(CrearExamenCriterioDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void applyUpdate(@MappingTarget ExamenCriterioEntity entity, ActualizarExamenCriterioDto dto);

    ExamenCriterioDto toDto(ExamenCriterioEntity entity);

    List<ExamenCriterioDto> toDtoList(List<ExamenCriterioEntity> entities);
}
