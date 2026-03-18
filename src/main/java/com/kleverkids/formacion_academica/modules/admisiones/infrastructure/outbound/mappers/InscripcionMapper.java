package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.ActualizarEstadoInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion.CrearInscripcionDto;
import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Inscripcion;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    Inscripcion toDomainModel(InscripcionEntity entity);

    List<Inscripcion> toDomainModelList(List<InscripcionEntity> entities);

    default InscripcionEntity toEntity(CrearInscripcionDto dto) {
        InscripcionEntity entity = new InscripcionEntity();
        entity.setEstudianteId(dto.getEstudianteId());
        entity.setPeriodoAcademico(dto.getPeriodoAcademico());
        entity.setFechaSolicitud(dto.getFechaSolicitud());
        entity.setEstado("PENDIENTE");
        entity.setObservaciones(dto.getObservaciones());
        return entity;
    }

    default void applyEstado(InscripcionEntity entity, ActualizarEstadoInscripcionDto dto) {
    }
}
