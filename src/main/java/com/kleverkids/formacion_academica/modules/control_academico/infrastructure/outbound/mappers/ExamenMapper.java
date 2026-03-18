package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ReglaCalificacionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CalificacionPersonalizadaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.ExamenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Mapper(componentModel = "spring", imports = {Long.class, ThreadLocalRandom.class})
public interface ExamenMapper {

    @Mapping(target = "reglas", source = "reglasCalificacion")
    ExamenEntity toEntity(CrearExamenDto dto);

    @Mapping(target = "reglasCalificacion", source = "reglas")
    Examen toDomainModel(ExamenEntity entity);

    ExamenEntity.ReglaCalificacionEmbeddable toEmbeddable(ReglaCalificacionDto dto);

    CalificacionPersonalizadaEntity toCalificacionEntity(RegistrarCalificacionPersonalizadaDto dto);

    CalificacionPersonalizadaDto toCalificacionDto(CalificacionPersonalizadaEntity entity);
}
