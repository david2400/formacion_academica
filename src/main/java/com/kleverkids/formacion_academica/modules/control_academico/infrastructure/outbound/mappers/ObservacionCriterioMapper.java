package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ObservacionCriterioEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Mapper(componentModel = "spring", imports = {Long.class, ThreadLocalRandom.class})
public interface ObservacionCriterioMapper {


    ObservacionCriterioEntity toEntity(RegistrarObservacionCriterioDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "criterioExamenId", ignore = true)
    @Mapping(target = "estudianteId", ignore = true)
    void applyUpdate(@MappingTarget ObservacionCriterioEntity entity, ActualizarObservacionCriterioDto dto);

    ObservacionCriterioDto toDto(ObservacionCriterioEntity entity);

    List<ObservacionCriterioDto> toDtoList(List<ObservacionCriterioEntity> entities);
}
