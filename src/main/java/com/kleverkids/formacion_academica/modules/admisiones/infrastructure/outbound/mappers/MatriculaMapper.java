package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.CrearMatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula.MatriculaDto;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MatriculaMapper {

    MatriculaDto toDto(MatriculaEntity entity);

    List<MatriculaDto> toDtoList(List<MatriculaEntity> entities);

    default MatriculaEntity toEntity(CrearMatriculaDto dto) {
        MatriculaEntity entity = new MatriculaEntity();
        entity.setId(UUID.randomUUID());
        entity.setInscripcionId(dto.getInscripcionId());
        entity.setEstudianteId(dto.getEstudianteId());
        entity.setGradoId(dto.getGradoId());
        entity.setGrupoId(dto.getGrupoId());
        entity.setFechaMatricula(dto.getFechaMatricula());
        entity.setEstado("ACTIVA");
        return entity;
    }
}
