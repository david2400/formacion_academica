package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.ActualizarCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CrearCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.CriterioEvaluadosEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CriterioEvaluadosMapper {

    CriterioEvaluadosEntity toEntity(CrearCriterioEvaluadosDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void applyUpdate(@MappingTarget CriterioEvaluadosEntity entity, ActualizarCriterioEvaluadosDto dto);

    CriterioEvaluadosDto toDto(CriterioEvaluadosEntity entity);

    List<CriterioEvaluadosDto> toDtoList(List<CriterioEvaluadosEntity> entities);
}
