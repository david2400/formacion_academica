package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grado;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GradoMapper {

    @Mapping(target = "nivelEducativoId", source = "nivelEducativoId")
    @Mapping(target = "activo", source = "activo")
    @Mapping(target = "usrCrea", source = "usrCrea")
    @Mapping(target = "usrMod", source = "usrMod")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "nivelEducativo", ignore = true)
    Grado toDomainModel(GradoEntity entity);

    List<Grado> toDomainModelList(List<GradoEntity> entities);

    default GradoEntity toEntity(CrearGradoDto dto) {
        GradoEntity entity = new GradoEntity();
        entity.setNombre(dto.getNombre());
        entity.setNivelEducativoId((dto.getNivelEducativoId()));
        entity.setActivo(true);
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

        // Audit fields are handled by JPA @PreUpdate
    }

    // Método de conversión de Instant a LocalDateTime
    @Named("instantToLocalDateTime")
    default LocalDateTime instantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
