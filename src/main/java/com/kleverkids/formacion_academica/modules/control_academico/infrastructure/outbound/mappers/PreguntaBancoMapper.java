package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearRespuestaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.RespuestaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaBancoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class PreguntaBancoMapper {

    private PreguntaBancoMapper() {
    }

    public static PreguntaBancoEntity toEntity(CrearPreguntaBancoDto dto) {
        PreguntaBancoEntity entity = new PreguntaBancoEntity();
        entity.setId(UUID.randomUUID());
        entity.setTematicaId(dto.tematicaId());
        entity.setEnunciado(dto.enunciado());
        entity.setTipo(dto.tipo());
        entity.setNivelDificultad(dto.nivelDificultad());
        entity.setPuntaje(dto.puntaje());
        entity.setRespuestas(mapRespuestas(dto.respuestas(), entity));
        return entity;
    }

    private static List<RespuestaBancoEntity> mapRespuestas(List<CrearRespuestaBancoDto> respuestasDto,
                                                            PreguntaBancoEntity parent) {
        if (respuestasDto == null || respuestasDto.isEmpty()) {
            return List.of();
        }
        List<RespuestaBancoEntity> respuestas = new ArrayList<>();
        for (CrearRespuestaBancoDto respuestaDto : respuestasDto) {
            RespuestaBancoEntity respuesta = new RespuestaBancoEntity();
            respuesta.setId(UUID.randomUUID());
            respuesta.setPregunta(parent);
            respuesta.setTexto(respuestaDto.texto());
            respuesta.setEsCorrecta(respuestaDto.esCorrecta());
            respuestas.add(respuesta);
        }
        return respuestas;
    }

    public static void applyUpdate(PreguntaBancoEntity entity, ActualizarPreguntaBancoDto dto) {
        if (dto.enunciado() != null) {
            entity.setEnunciado(dto.enunciado());
        }
        if (dto.tipo() != null) {
            entity.setTipo(dto.tipo());
        }
        if (dto.nivelDificultad() != null) {
            entity.setNivelDificultad(dto.nivelDificultad());
        }
        if (dto.puntaje() != null) {
            entity.setPuntaje(dto.puntaje());
        }
    }

    public static PreguntaBancoDto toDto(PreguntaBancoEntity entity) {
        List<RespuestaBancoDto> respuestas = entity.getRespuestas().stream()
                .map(r -> new RespuestaBancoDto(
                        r.getId(),
                        entity.getId(),
                        r.getTexto(),
                        r.isEsCorrecta()
                )).toList();
        return new PreguntaBancoDto(
                entity.getId(),
                entity.getTematicaId(),
                entity.getEnunciado(),
                entity.getTipo(),
                entity.getNivelDificultad(),
                entity.getPuntaje(),
                respuestas
        );
    }

    public static List<PreguntaBancoDto> toDtoList(List<PreguntaBancoEntity> entities) {
        return entities.stream().map(PreguntaBancoMapper::toDto).toList();
    }
}
