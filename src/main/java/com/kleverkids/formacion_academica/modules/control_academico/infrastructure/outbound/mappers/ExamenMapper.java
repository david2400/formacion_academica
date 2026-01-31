package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ReglaCalificacionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CalificacionPersonalizadaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ExamenEntity;

import java.util.List;
import java.util.UUID;

public final class ExamenMapper {

    private ExamenMapper() {
    }

    public static ExamenEntity toEntity(CrearExamenDto dto) {
        ExamenEntity entity = new ExamenEntity();
        entity.setId(UUID.randomUUID());
        entity.setClaseId(dto.claseId());
        entity.setNombre(dto.nombre());
        entity.setFecha(dto.fecha());
        entity.setReglas(dto.reglasCalificacion().stream()
                .map(ExamenMapper::toEmbeddable)
                .toList());
        return entity;
    }

    public static ExamenDto toDto(ExamenEntity entity) {
        List<ReglaCalificacionDto> reglas = entity.getReglas().stream()
                .map(r -> new ReglaCalificacionDto(r.getCriterio(), r.getPonderacion(), r.getPuntajeMaximo()))
                .toList();
        return new ExamenDto(entity.getId(), entity.getClaseId(), entity.getNombre(), entity.getFecha(), reglas);
    }

    private static ExamenEntity.ReglaCalificacionEmbeddable toEmbeddable(ReglaCalificacionDto dto) {
        ExamenEntity.ReglaCalificacionEmbeddable emb = new ExamenEntity.ReglaCalificacionEmbeddable();
        emb.setCriterio(dto.criterio());
        emb.setPonderacion(dto.ponderacion());
        emb.setPuntajeMaximo(dto.puntajeMaximo());
        return emb;
    }

    public static CalificacionPersonalizadaEntity toEntity(RegistrarCalificacionPersonalizadaDto dto) {
        CalificacionPersonalizadaEntity entity = new CalificacionPersonalizadaEntity();
        entity.setId(UUID.randomUUID());
        entity.setExamenId(dto.examenId());
        entity.setEstudianteId(dto.estudianteId());
        entity.setCriterio(dto.criterio());
        entity.setPuntajeOtorgado(dto.puntajeOtorgado());
        return entity;
    }

    public static CalificacionPersonalizadaDto toDto(CalificacionPersonalizadaEntity entity) {
        return new CalificacionPersonalizadaDto(
                entity.getExamenId(),
                entity.getEstudianteId(),
                entity.getCriterio(),
                entity.getPuntajeOtorgado()
        );
    }
}
