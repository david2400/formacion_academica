package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.IntentoExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.RespuestaIntentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


@Mapper(componentModel = "spring", imports = {Long.class, LocalDateTime.class, ArrayList.class, ThreadLocalRandom.class})
public interface IntentoExamenMapper {

//    @Mapping(target = "estado", constant = "EN_PROGRESO")
//    @Mapping(target = "iniciadoEn", expression = "java(LocalDateTime.now())")
//    @Mapping(target = "respuestas", expression = "java(new ArrayList<>())")
//    @Mapping(target = "finalizadoEn", ignore = true)
//    @Mapping(target = "puntajeTotal", ignore = true)
    IntentoExamenEntity toEntity(IniciarIntentoExamenDto dto);

    IntentoExamenDto toDto(IntentoExamenEntity entity);

    @Mapping(target = "intentoId", source = "intento.id")
    RespuestaIntentoDto toRespuestaDto(RespuestaIntentoEntity entity);

    @Mapping(target = "intento", source = "intento")
    @Mapping(target = "preguntaId", source = "dto.preguntaId")
    @Mapping(target = "respuesta", source = "dto.respuesta")
    @Mapping(target = "esCorrecta", expression = "java(Boolean.TRUE.equals(dto.esCorrecta()))")
    @Mapping(target = "puntajeObtenido", source = "dto.puntajeObtenido")
    RespuestaIntentoEntity toEntity(RegistrarRespuestaIntentoDto dto, IntentoExamenEntity intento);

    default void applyFinalizacion(@MappingTarget IntentoExamenEntity entity, FinalizarIntentoExamenDto dto) {
        entity.setEstado("FINALIZADO");
        entity.setFinalizadoEn(LocalDateTime.now());
        entity.setPuntajeTotal(dto.puntajeTotal());
    }

    List<IntentoExamenDto> toDtoList(List<IntentoExamenEntity> entities);
}
