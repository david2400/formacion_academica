package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.RespuestaPreguntaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, LocalDateTime.class})
public interface RespuestaPreguntaMapper {

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "estudianteExamen", source = "relacion")
    @Mapping(target = "examenId", source = "dto.examenId")
    @Mapping(target = "estudianteId", source = "dto.estudianteId")
    @Mapping(target = "preguntaId", source = "dto.preguntaId")
    @Mapping(target = "respuestaBancoId", source = "dto.respuestaBancoId")
    @Mapping(target = "respuestaTexto", source = "dto.respuestaTexto")
    @Mapping(target = "esCorrecta", source = "dto.esCorrecta")
    @Mapping(target = "puntajeObtenido", source = "dto.puntajeObtenido")
    @Mapping(target = "registradaEn", expression = "java(LocalDateTime.now())")
    RespuestaPreguntaEntity toEntity(RegistrarRespuestaPreguntaPersistenceDto dto, EstudianteExamenEntity relacion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estudianteExamen", ignore = true)
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "estudianteId", ignore = true)
    @Mapping(target = "preguntaId", ignore = true)
    @Mapping(target = "registradaEn", ignore = true)
    void applyUpdate(@MappingTarget RespuestaPreguntaEntity entity, ActualizarRespuestaPreguntaDto dto);

    @Mapping(target = "estudianteExamenId", source = "estudianteExamen.id")
    RespuestaPreguntaDto toDto(RespuestaPreguntaEntity entity);

    List<RespuestaPreguntaDto> toDtoList(List<RespuestaPreguntaEntity> entities);
}
