package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Mapper para convertir entre Entity y DTO de Estados
 * Sigue el patrón MapStruct de los demás módulos
 */
@Mapper(componentModel = "spring")
public interface EstadoMapper {
    
    EstadoMapper INSTANCE = Mappers.getMapper(EstadoMapper.class);
    
    // Entity a DTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "codigo", source = "codigo")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "descripcion", source = "descripcion")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "icono", source = "icono")
    @Mapping(target = "idModulo", source = "idModulo")
    @Mapping(target = "idEmpresa", source = "idEmpresa")
    @Mapping(target = "esInicial", source = "esInicial")
    @Mapping(target = "esFinal", source = "esFinal")
    @Mapping(target = "orden", source = "orden")
    @Mapping(target = "activo", source = "activo")
    @Mapping(target = "metadata", source = "metadata")
    @Mapping(target = "creadoEn", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "actualizadoEn", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    EstadoDto toDto(EstadoEntity entity);
    
    // DTO a Entity
    default EstadoEntity toEntity(EstadoDto dto) {
        EstadoEntity entity = new EstadoEntity();
        entity.setUuid(dto.uuid());
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setDescripcion(dto.descripcion());
        entity.setColor(dto.color());
        entity.setIcono(dto.icono());
        entity.setIdModulo(dto.idModulo());
        entity.setIdEmpresa(dto.idEmpresa());
        entity.setEsInicial(dto.esInicial());
        entity.setEsFinal(dto.esFinal());
        entity.setOrden(dto.orden());
        entity.setActivo(dto.activo());
        entity.setMetadata(dto.metadata());
        // Las relaciones y propiedades de auditoría son manejadas por JPA
        return entity;
    }
    
    // Listas
    List<EstadoDto> toDtoList(List<EstadoEntity> entities);
    List<EstadoEntity> toEntityList(List<EstadoDto> dtos);
    
    // Update partial (para actualizaciones)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "idModulo", ignore = true)
    @Mapping(target = "idEmpresa", ignore = true)
    void updateEntityFromDto(EstadoDto dto, @MappingTarget EstadoEntity entity);

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
}
