package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.ActualizarMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MatriculaMapper {

    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "eliminado", constant = "false")
    Matricula toDomainModel(MatriculaEntity entity);

    List<Matricula> toDomainModelList(List<MatriculaEntity> entities);

    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eliminado", ignore = true)
    @Mapping(target = "estadoId", constant = "1") // Default active state
    MatriculaEntity toEntity(CrearMatriculaDto dto);

    @Named("instantToLocalDateTime")
    static LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()) : null;
    }

    @Named("localDateTimeToInstant")
    static Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant() : null;
    }
}
