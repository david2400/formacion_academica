package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.NivelEducativoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface GradoMapper {

    @Mapping(target = "nivelEducativoId", source = "nivelEducativoId")
    Grado toDomainModel(GradoEntity entity);

    default GradoEntity toEntity(CrearGradoDto dto) {
        GradoEntity entity = new GradoEntity();
        entity.setNombre(dto.getNombre());
        entity.setNivelEducativoId((dto.getNivelEducativoId()));
        entity.setOrden(dto.getOrden());
        // Audit fields are handled by JPA @PrePersist
        return entity;
    }

    default void updateEntityFromDto(ActualizarGradoDto dto, GradoEntity entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        if (dto.getNombre() != null) {
            entity.setNombre(dto.getNombre());
        }
        
        if (dto.getNivelEducativoId() != null) {
            entity.setNivelEducativoId((dto.getNivelEducativoId()));
        }
        
        if (dto.getOrden() != null) {
            entity.setOrden(dto.getOrden());
        }
        
        // Audit fields are handled by JPA @PreUpdate
    }

}
