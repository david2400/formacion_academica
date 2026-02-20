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
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface TematicaMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "examenId", source = "dto.examenId")
    TematicaEntity toEntity(CrearTematicaDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "id", ignore = true)
    void applyUpdate(@MappingTarget TematicaEntity entity, ActualizarTematicaDto dto);

    TematicaDto toDto(TematicaEntity entity);

    List<TematicaDto> toDtoList(List<TematicaEntity> entities);
}
