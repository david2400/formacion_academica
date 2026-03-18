package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.clase.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClaseMapper {

    @Mapping(target = "id", ignore = true)
    ClaseEntity toEntity(CrearClaseDto dto);

    Clase toDomainModel(ClaseEntity entity);

    List<ClaseEntity> toEntityList(List<CrearClaseDto> dtos);

    List<Clase> toDomainModelList(List<ClaseEntity> entities);
}
