package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.EstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen.RegistrarEstudianteExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, LocalDateTime.class, ArrayList.class})
public interface EstudianteExamenMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "asignadoEn", expression = "java(LocalDateTime.now())")
    @Mapping(target = "respuestas", expression = "java(new ArrayList<>())")
    EstudianteExamenEntity toEntity(RegistrarEstudianteExamenDto dto);

    EstudianteExamenDto toDto(EstudianteExamenEntity entity);

    List<EstudianteExamenDto> toDtoList(List<EstudianteExamenEntity> entities);
}
