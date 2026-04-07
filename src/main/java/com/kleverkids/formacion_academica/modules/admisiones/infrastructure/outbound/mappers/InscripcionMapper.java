package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    @Mapping(target = "eliminado", constant = "false")
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Inscripcion toDomainModel(InscripcionEntity entity);

    List<Inscripcion> toDomainModelList(List<InscripcionEntity> entities);

    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eliminado", ignore = true)
    @Mapping(target = "estadoId", constant = "1") // Default active state
    InscripcionEntity toEntity(CrearInscripcionDto dto);

    @Named("instantToLocalDateTime")
    static LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()) : null;
    }

    @Named("localDateTimeToInstant")
    static Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant() : null;
    }
}
