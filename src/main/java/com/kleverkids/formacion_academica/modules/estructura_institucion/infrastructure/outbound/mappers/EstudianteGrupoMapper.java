package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EstudianteGrupoMapper {

    @Mapping(target = "estado", source = "estadoLegacy")
    @Mapping(target = "activo", source = "activo")
    @Mapping(target = "usrCrea", source = "usrCrea")
    @Mapping(target = "usrMod", source = "usrMod")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    EstudianteGrupo toDomainModel(EstudianteGrupoEntity entity);

    List<EstudianteGrupo> toDomainModelList(List<EstudianteGrupoEntity> entities);

    default EstudianteGrupoEntity toEntity(AsignarEstudianteGrupoDto dto) {
        EstudianteGrupoEntity entity = new EstudianteGrupoEntity();
        entity.setEstudianteId(dto.estudianteId());
        entity.setGrupoId(dto.grupoId());
        entity.setFechaAsignacion(dto.fechaAsignacion());
        entity.setEstadoLegacy("ASIGNADO");
        entity.setActivo(true);
        // Las propiedades de auditoría son manejadas por JPA
        return entity;
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
