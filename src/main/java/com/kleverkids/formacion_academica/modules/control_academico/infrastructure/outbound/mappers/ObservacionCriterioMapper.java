package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ObservacionCriterioEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface ObservacionCriterioMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    ObservacionCriterioEntity toEntity(RegistrarObservacionCriterioDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "criterioId", ignore = true)
    @Mapping(target = "estudianteId", ignore = true)
    void applyUpdate(@MappingTarget ObservacionCriterioEntity entity, ActualizarObservacionCriterioDto dto);

    ObservacionCriterioDto toDto(ObservacionCriterioEntity entity);

    List<ObservacionCriterioDto> toDtoList(List<ObservacionCriterioEntity> entities);
}
