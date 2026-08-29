package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.AsignacionExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen.CrearAsignacionExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.asignacion_examen.AsignacionExamenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AsignacionExamenMapper {

    @Mapping(target = "examenNombre", source = "examenNombre")
    @Mapping(target = "claseNombre", source = "claseNombre")
    AsignacionExamenDto toDto(AsignacionExamenEntity entity, String examenNombre, String claseNombre);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "PROGRAMADA")
    @Mapping(target = "grado", source = "grado")
    @Mapping(target = "grupo", source = "grupo")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AsignacionExamenEntity toEntity(CrearAsignacionExamenDto dto, String grado, String grupo);
}
