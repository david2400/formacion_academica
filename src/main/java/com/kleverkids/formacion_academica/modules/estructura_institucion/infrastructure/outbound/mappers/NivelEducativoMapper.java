package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.ActualizarNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico.CrearNivelEducativoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
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

    @Mapping(target = "nivelSuperior", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "activo", ignore = true)
    NivelEducativoEntity toEntity(CrearNivelEducativoDto dto);
    
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
    
    // Entity a Domain Model
//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "codigo", source = "codigo")
//    @Mapping(target = "nombre", source = "nombre")
//    @Mapping(target = "descripcion", source = "descripcion")
//    @Mapping(target = "orden", source = "orden")
//    @Mapping(target = "nivelSuperiorId", source = "nivelSuperior", qualifiedByName = "mapNivelSuperiorToId")
    @Mapping(target = "activo", source = "activo")
    @Mapping(target = "usrCrea", source = "usrCrea")
    @Mapping(target = "usrMod", source = "usrMod")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    NivelEducativo toDomainModel(NivelEducativoEntity entity);
    
    List<NivelEducativo> toDomainModelList(List<NivelEducativoEntity> entities);

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
