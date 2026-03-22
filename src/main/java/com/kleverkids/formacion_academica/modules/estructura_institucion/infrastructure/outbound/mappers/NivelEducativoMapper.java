package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel.NivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.NivelEducativoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface NivelEducativoMapper {
    
    // DTO a Entity
    default NivelEducativoEntity toEntity(CrearNivelEducativoDto dto) {
        NivelEducativoEntity entity = new NivelEducativoEntity();
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setOrden(dto.orden());
        entity.setActivo(true);
        entity.setCategoria(dto.categoria());
        // nivelSuperior se maneja por separado si es necesario
        // Las propiedades de auditoría son manejadas por JPA
        return entity;
    }
    
    // Update partial (para actualizaciones)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigo", ignore = true)
    @Mapping(target = "nivelSuperior", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateEntity(@MappingTarget NivelEducativoEntity entity, ActualizarNivelEducativoDto dto);
    
    // Entity a DTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "orden", source = "orden")
    @Mapping(target = "nivelSuperiorId", source = "nivelSuperior", qualifiedByName = "mapNivelSuperiorToId")
    @Mapping(target = "creadoEn", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "actualizadoEn", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    NivelEducativoDto toDto(NivelEducativoEntity entity);
    
    List<NivelEducativoDto> toDtoList(List<NivelEducativoEntity> entities);

    // Métodos personalizados para conversión de tipos
    @Named("instantToLocalDateTime")
    default LocalDateTime instantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Named("localDateTimeToInstant")
    default Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    @Named("mapNivelSuperiorToId")
    default Long mapNivelSuperiorToId(NivelEducativoEntity nivelSuperior) {
        if (nivelSuperior == null) {
            return null;
        }
        return nivelSuperior.getId();
    }
}
