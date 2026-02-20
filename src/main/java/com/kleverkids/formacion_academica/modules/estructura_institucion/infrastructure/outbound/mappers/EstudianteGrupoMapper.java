package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EstudianteGrupoMapper {

    EstudianteGrupoDto toDto(EstudianteGrupoEntity entity);

    List<EstudianteGrupoDto> toDtoList(List<EstudianteGrupoEntity> entities);

    default EstudianteGrupoEntity toEntity(AsignarEstudianteGrupoDto dto) {
        EstudianteGrupoEntity entity = new EstudianteGrupoEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteId(dto.estudianteId());
        entity.setGrupoId(dto.grupoId());
        entity.setFechaAsignacion(dto.fechaAsignacion());
        entity.setEstado("ASIGNADO");
        return entity;
    }
}
