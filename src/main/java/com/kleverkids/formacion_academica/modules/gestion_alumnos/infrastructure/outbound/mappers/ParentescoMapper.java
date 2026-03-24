package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.CrearParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.ActualizarParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.ParentescoEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParentescoMapper {

    Parentesco toDomainModel(ParentescoEntity entity);

    List<Parentesco> toDomainModelList(List<ParentescoEntity> entities);

    ParentescoEntity toEntity(CrearParentescoDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@MappingTarget ParentescoEntity entity, ActualizarParentescoDto dto);
}
