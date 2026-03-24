package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearRespuestaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.PreguntaBanco;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.RespuestaBanco;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.RespuestaBancoEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", imports = {ArrayList.class})
public interface PreguntaBancoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "respuestas", ignore = true)
    PreguntaBancoEntity toEntity(CrearPreguntaBancoDto dto);

    @AfterMapping
    default void mapRespuestas(CrearPreguntaBancoDto dto, @MappingTarget PreguntaBancoEntity entity) {
        if (dto.getRespuestas() == null || dto.getRespuestas().isEmpty()) {
            entity.setRespuestas(List.of());
            return;
        }
        List<RespuestaBancoEntity> respuestas = new ArrayList<>();
        for (CrearRespuestaBancoDto respuestaDto : dto.getRespuestas()) {
            RespuestaBancoEntity respuesta = new RespuestaBancoEntity();
            respuesta.setId(null);
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

    @Mapping(target = "respuestas", source = "respuestas")
    PreguntaBanco toDomainModel(PreguntaBancoEntity entity);

    @Mapping(target = "respuestas", source = "respuestas")
    List<PreguntaBanco> toDomainModelList(List<PreguntaBancoEntity> entities);
}
