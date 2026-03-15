package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrupoMapper {

    GrupoDto toDto(GrupoEntity entity);

    List<GrupoDto> toDtoList(List<GrupoEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ActualizarGrupoDto dto, @MappingTarget GrupoEntity entity);

    default GrupoEntity toEntity(CrearGrupoDto dto) {
        GrupoEntity entity = new GrupoEntity();
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setGradoId(dto.gradoId());
        entity.setCapacidadMaxima(dto.capacidadMaxima());
        entity.setTutorId(dto.tutorId());
        entity.setAulaId(dto.aulaId());
        entity.setActivo(true);
        return entity;
    }
}
