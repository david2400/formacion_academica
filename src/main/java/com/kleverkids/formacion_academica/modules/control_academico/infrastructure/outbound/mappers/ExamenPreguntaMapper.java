package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ActualizarExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.CrearExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ExamenPreguntaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExamenPreguntaMapper {

    ExamenPreguntaEntity toEntity(CrearExamenPreguntaDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void applyUpdate(@MappingTarget ExamenPreguntaEntity entity, ActualizarExamenPreguntaDto dto);

    ExamenPreguntaDto toDto(ExamenPreguntaEntity entity);

    List<ExamenPreguntaDto> toDtoList(List<ExamenPreguntaEntity> entities);
}
