package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearRespuestaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.RespuestaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.RespuestaBancoEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, ArrayList.class})
public interface PreguntaBancoMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "respuestas", ignore = true)
    PreguntaBancoEntity toEntity(CrearPreguntaBancoDto dto);

    @AfterMapping
    default void mapRespuestas(CrearPreguntaBancoDto dto, @MappingTarget PreguntaBancoEntity entity) {
        if (dto.respuestas() == null || dto.respuestas().isEmpty()) {
            entity.setRespuestas(List.of());
            return;
        }
        List<RespuestaBancoEntity> respuestas = new ArrayList<>();
        for (CrearRespuestaBancoDto respuestaDto : dto.respuestas()) {
            RespuestaBancoEntity respuesta = new RespuestaBancoEntity();
            respuesta.setId(UUID.randomUUID());
            respuesta.setPregunta(entity);
            respuesta.setTexto(respuestaDto.texto());
            respuesta.setEsCorrecta(respuestaDto.esCorrecta());
            respuestas.add(respuesta);
        }
        entity.setRespuestas(respuestas);
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tematicaId", ignore = true)
    @Mapping(target = "respuestas", ignore = true)
    void applyUpdate(@MappingTarget PreguntaBancoEntity entity, ActualizarPreguntaBancoDto dto);

    default PreguntaBancoDto toDto(PreguntaBancoEntity entity) {
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

    List<PreguntaBancoDto> toDtoList(List<PreguntaBancoEntity> entities);
}
