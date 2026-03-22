package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.CriterioExamenEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CriterioExamenMapper {

    @Mapping(target = "id", ignore = true)
    CriterioExamenEntity toEntity(CrearCriterioExamenDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "examenId", ignore = true)
    void applyUpdate(@MappingTarget CriterioExamenEntity entity, ActualizarCriterioExamenDto dto);

    CriterioExamenDto toDto(CriterioExamenEntity entity);

    List<CriterioExamenDto> toDtoList(List<CriterioExamenEntity> entities);
}
