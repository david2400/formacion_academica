package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaPreguntaEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public final class RespuestaPreguntaMapper {

    private RespuestaPreguntaMapper() {
    }

    public static RespuestaPreguntaEntity toEntity(RegistrarRespuestaPreguntaPersistenceDto dto,
                                                   EstudianteExamenEntity relacion) {
        RespuestaPreguntaEntity entity = new RespuestaPreguntaEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteExamen(relacion);
        entity.setExamenId(dto.examenId());
        entity.setEstudianteId(dto.estudianteId());
        entity.setPreguntaId(dto.preguntaId());
        entity.setRespuestaBancoId(dto.respuestaBancoId());
        entity.setRespuestaTexto(dto.respuestaTexto());
        entity.setEsCorrecta(dto.esCorrecta());
        entity.setPuntajeObtenido(dto.puntajeObtenido());
        entity.setRegistradaEn(LocalDateTime.now());
        return entity;
    }

    public static void applyUpdate(RespuestaPreguntaEntity entity, ActualizarRespuestaPreguntaDto dto) {
        if (dto.respuestaTexto() != null && !dto.respuestaTexto().isBlank()) {
            entity.setRespuestaTexto(dto.respuestaTexto());
        }
        if (dto.respuestaBancoId() != null) {
            entity.setRespuestaBancoId(dto.respuestaBancoId());
        }
        if (dto.esCorrecta() != null) {
            entity.setEsCorrecta(dto.esCorrecta());
        }
        if (dto.puntajeObtenido() != null) {
            entity.setPuntajeObtenido(dto.puntajeObtenido());
        }
    }

    public static RespuestaPreguntaDto toDto(RespuestaPreguntaEntity entity) {
        return new RespuestaPreguntaDto(
                entity.getId(),
                entity.getEstudianteExamen().getId(),
                entity.getExamenId(),
                entity.getEstudianteId(),
                entity.getPreguntaId(),
                entity.getRespuestaBancoId(),
                entity.getRespuestaTexto(),
                entity.getEsCorrecta(),
                entity.getPuntajeObtenido(),
                entity.getRegistradaEn()
        );
    }

    public static List<RespuestaPreguntaDto> toDtoList(List<RespuestaPreguntaEntity> entities) {
        return entities.stream().map(RespuestaPreguntaMapper::toDto).toList();
    }
}
