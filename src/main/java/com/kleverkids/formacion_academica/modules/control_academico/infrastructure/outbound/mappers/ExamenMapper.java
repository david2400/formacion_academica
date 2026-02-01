package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ReglaCalificacionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CalificacionPersonalizadaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.ExamenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class})
public interface ExamenMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "reglas", source = "reglasCalificacion")
    ExamenEntity toEntity(CrearExamenDto dto);

    ExamenEntity.ReglaCalificacionEmbeddable toEmbeddable(ReglaCalificacionDto dto);

    default ExamenDto toDto(ExamenEntity entity) {
        List<ReglaCalificacionDto> reglas = entity.getReglas().stream()
                .map(r -> new ReglaCalificacionDto(r.getCriterio(), r.getPonderacion(), r.getPuntajeMaximo()))
                .toList();
        return new ExamenDto(entity.getId(), entity.getClaseId(), entity.getNombre(), entity.getFecha(), reglas);
    }

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    CalificacionPersonalizadaEntity toCalificacionEntity(RegistrarCalificacionPersonalizadaDto dto);

    CalificacionPersonalizadaDto toCalificacionDto(CalificacionPersonalizadaEntity entity);
}
