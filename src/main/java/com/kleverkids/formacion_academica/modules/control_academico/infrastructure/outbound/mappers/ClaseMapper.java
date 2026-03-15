package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClaseMapper {

    @Mapping(target = "id", ignore = true)
    ClaseEntity toEntity(CrearClaseDto dto);

    ClaseDto toDto(ClaseEntity entity);

    List<ClaseEntity> toEntityList(List<CrearClaseDto> dtos);

    List<ClaseDto> toDtoList(List<ClaseEntity> entities);
}
