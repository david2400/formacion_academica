package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface AulaMapper {

    AulaDto toDto(AulaEntity entity);

    List<AulaDto> toDtoList(List<AulaEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ActualizarAulaDto dto, @MappingTarget AulaEntity entity);

    default AulaEntity toEntity(CrearAulaDto dto) {
        AulaEntity entity = new AulaEntity();
        entity.setId(UUID.randomUUID());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setCapacidad(dto.capacidad());
        entity.setActivo(dto.activo() != null ? dto.activo() : true);
        return entity;
    }
}
