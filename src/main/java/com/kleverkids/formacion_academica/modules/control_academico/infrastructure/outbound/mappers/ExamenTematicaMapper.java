package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.CrearExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.ExamenTematicaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamenTematicaMapper {

    ExamenTematicaEntity toEntity(CrearExamenTematicaDto dto);

    ExamenTematicaDto toDto(ExamenTematicaEntity entity);

    List<ExamenTematicaDto> toDtoList(List<ExamenTematicaEntity> entities);
}
