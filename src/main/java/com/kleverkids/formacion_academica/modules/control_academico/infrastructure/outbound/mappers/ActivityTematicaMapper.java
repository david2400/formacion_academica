package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.CrearActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityTematicaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityTematicaMapper {

    ActivityTematicaEntity toEntity(CrearActivityTematicaDto dto);

    ActivityTematicaDto toDto(ActivityTematicaEntity entity);

    List<ActivityTematicaDto> toDtoList(List<ActivityTematicaEntity> entities);
}
