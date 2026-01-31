package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.IntentoExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaIntentoEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class IntentoExamenMapper {

    private IntentoExamenMapper() {
    }

    public static IntentoExamenEntity toEntity(IniciarIntentoExamenDto dto) {
        IntentoExamenEntity entity = new IntentoExamenEntity();
        entity.setId(UUID.randomUUID());
        entity.setExamenId(dto.examenId());
        entity.setEstudianteId(dto.estudianteId());
        entity.setEstado("EN_PROGRESO");
        entity.setIniciadoEn(LocalDateTime.now());
        entity.setRespuestas(new ArrayList<>());
        return entity;
    }

    public static IntentoExamenDto toDto(IntentoExamenEntity entity) {
        List<RespuestaIntentoDto> respuestas = entity.getRespuestas().stream()
                .map(IntentoExamenMapper::toDto)
                .toList();
        return new IntentoExamenDto(
                entity.getId(),
                entity.getExamenId(),
                entity.getEstudianteId(),
                entity.getEstado(),
                entity.getIniciadoEn(),
                entity.getFinalizadoEn(),
                entity.getPuntajeTotal(),
                respuestas
        );
    }

    public static RespuestaIntentoDto toDto(RespuestaIntentoEntity entity) {
        return new RespuestaIntentoDto(
                entity.getId(),
                entity.getIntento().getId(),
                entity.getPreguntaId(),
                entity.getRespuesta(),
                entity.isEsCorrecta(),
                entity.getPuntajeObtenido()
        );
    }

    public static RespuestaIntentoEntity toEntity(RegistrarRespuestaIntentoDto dto, IntentoExamenEntity intento) {
        RespuestaIntentoEntity entity = new RespuestaIntentoEntity();
        entity.setId(UUID.randomUUID());
        entity.setIntento(intento);
        entity.setPreguntaId(dto.preguntaId());
        entity.setRespuesta(dto.respuesta());
        entity.setEsCorrecta(Boolean.TRUE.equals(dto.esCorrecta()));
        entity.setPuntajeObtenido(dto.puntajeObtenido());
        return entity;
    }

    public static void applyFinalizacion(IntentoExamenEntity entity, FinalizarIntentoExamenDto dto) {
        entity.setEstado("FINALIZADO");
        entity.setFinalizadoEn(LocalDateTime.now());
        entity.setPuntajeTotal(dto.puntajeTotal());
    }
}
