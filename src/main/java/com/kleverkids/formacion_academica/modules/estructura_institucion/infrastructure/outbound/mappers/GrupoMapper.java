package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrupoMapper {

    // Entity a DTO
    @Mapping(target = "aulaId", ignore = true) // No hay mapeo directo de Set<AulaEntity> a Long
    @Mapping(target = "activo", source = "estado")
    GrupoDto toDto(GrupoEntity entity);

    List<GrupoDto> toDtoList(List<GrupoEntity> entities);

    // DTO a Entity - método manual para evitar conflictos con AuditInfo
    default GrupoEntity toEntity(CrearGrupoDto dto) {
        GrupoEntity entity = new GrupoEntity();
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setGradoId(dto.gradoId());
        entity.setCapacidadMaxima(dto.capacidadMaxima());
        entity.setTutorId(dto.tutorId());
        entity.setEstado(true); // Valor por defecto para nuevos grupos
        // Las relaciones (grado, aulas) y propiedades de auditoría son manejadas por separado
        return entity;
    }

    // Update partial
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "grado", ignore = true)
    @Mapping(target = "aulas", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDto(ActualizarGrupoDto dto, @MappingTarget GrupoEntity entity);
}
