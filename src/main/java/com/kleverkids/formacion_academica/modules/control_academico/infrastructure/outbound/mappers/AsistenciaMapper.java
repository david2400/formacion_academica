package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.AsistenciaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AsistenciaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "presente", ignore = true)
    AsistenciaEntity toEntity(RegistrarAsistenciaDto dto);

    AsistenciaDto toDto(AsistenciaEntity entity);

    List<AsistenciaDto> toDtoList(List<AsistenciaEntity> entities);
}
