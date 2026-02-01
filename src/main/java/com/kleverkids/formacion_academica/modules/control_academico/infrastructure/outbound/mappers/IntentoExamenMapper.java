package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.IntentoExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.RespuestaIntentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, LocalDateTime.class, ArrayList.class})
public interface IntentoExamenMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "estado", constant = "EN_PROGRESO")
    @Mapping(target = "iniciadoEn", expression = "java(LocalDateTime.now())")
    @Mapping(target = "respuestas", expression = "java(new ArrayList<>())")
    @Mapping(target = "finalizadoEn", ignore = true)
    @Mapping(target = "puntajeTotal", ignore = true)
    IntentoExamenEntity toEntity(IniciarIntentoExamenDto dto);

    default IntentoExamenDto toDto(IntentoExamenEntity entity) {
        List<RespuestaIntentoDto> respuestas = entity.getRespuestas().stream()
                .map(this::toRespuestaDto)
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

    @Mapping(target = "intentoId", source = "intento.id")
    RespuestaIntentoDto toRespuestaDto(RespuestaIntentoEntity entity);

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
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
