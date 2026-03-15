package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface GradoMapper {

    GradoDto toDto(GradoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    default GradoEntity toEntity(CrearGradoDto dto) {
        GradoEntity entity = new GradoEntity();
        entity.setNombre(dto.getNombre());
        entity.setNivelEducativo(dto.getNivelEducativo());
        entity.setOrden(dto.getOrden());
        return entity;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDto(ActualizarGradoDto dto, @MappingTarget GradoEntity entity);
}
