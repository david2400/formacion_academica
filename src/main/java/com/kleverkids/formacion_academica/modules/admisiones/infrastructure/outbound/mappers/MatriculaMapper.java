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

    Matricula toDomainModel(MatriculaEntity entity);

    List<Matricula> toDomainModelList(List<MatriculaEntity> entities);

    @Mapping(target = "inscripcionId", source = "inscripcionId")
    @Mapping(target = "estudianteId", source = "estudianteId")
    @Mapping(target = "gradoId", source = "gradoId")
    @Mapping(target = "grupoId", source = "grupoId")
    @Mapping(target = "fechaMatricula", source = "fechaMatricula")
    @Mapping(target = "renovacion", source = "renovacion")
    MatriculaEntity toEntity(CrearMatriculaDto dto);

    @Mapping(target = "inscripcionId", source = "dto.inscripcionId")
    @Mapping(target = "estudianteId", source = "dto.estudianteId")
    @Mapping(target = "gradoId", source = "dto.gradoId")
    @Mapping(target = "grupoId", source = "dto.grupoId")
    @Mapping(target = "fechaMatricula", source = "dto.fechaMatricula")
    @Mapping(target = "renovacion", source = "dto.renovacion")
    @Mapping(target = "estadoId", source = "dto.estadoId")
    @Mapping(target = "observaciones", source = "dto.observaciones")
    MatriculaEntity applyEstado(MatriculaEntity entity, ActualizarMatriculaDto dto);

    @Named("instantToLocalDateTime")
    static LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, java.time.ZoneId.systemDefault()) : null;
    }

    @Named("localDateTimeToInstant")
    static Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant() : null;
    }
}
