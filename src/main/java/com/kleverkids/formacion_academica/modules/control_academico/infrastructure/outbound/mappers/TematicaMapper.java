package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TematicaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "examenId", ignore = true)
    TematicaEntity toEntity(CrearTematicaDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "id", ignore = true)
    void applyUpdate(@MappingTarget TematicaEntity entity, ActualizarTematicaDto dto);

    @Mapping(target = "id", ignore = true)
    TematicaDto toDto(TematicaEntity entity);

    @Mapping(target = "id", ignore = true)
    List<TematicaDto> toDtoList(List<TematicaEntity> entities);
}
