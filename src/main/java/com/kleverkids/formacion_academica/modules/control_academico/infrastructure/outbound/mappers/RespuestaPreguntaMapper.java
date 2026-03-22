package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.RespuestaPreguntaEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface RespuestaPreguntaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estudianteExamen", source = "relacion")
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "estudianteId", ignore = true)
    @Mapping(target = "preguntaId", ignore = true)
    @Mapping(target = "respuestaBancoId", ignore = true)
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estudianteExamenId", ignore = true)
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "estudianteId", ignore = true)
    @Mapping(target = "preguntaId", ignore = true)
    @Mapping(target = "respuestaBancoId", ignore = true)
    RespuestaPreguntaDto toDto(RespuestaPreguntaEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estudianteExamenId", ignore = true)
    @Mapping(target = "examenId", ignore = true)
    @Mapping(target = "estudianteId", ignore = true)
    @Mapping(target = "preguntaId", ignore = true)
    @Mapping(target = "respuestaBancoId", ignore = true)
    List<RespuestaPreguntaDto> toDtoList(List<RespuestaPreguntaEntity> entities);
}
