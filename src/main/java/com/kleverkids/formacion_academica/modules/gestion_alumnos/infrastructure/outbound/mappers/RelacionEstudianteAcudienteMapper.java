package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.EstudianteAcudiente;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RelacionEstudianteAcudienteMapper {

    EstudianteAcudiente toDomain(EstudianteAcudienteEntity entity);

    List<EstudianteAcudiente> toDomainList(List<EstudianteAcudienteEntity> entities);

    EstudianteAcudienteEntity toEntity(CrearEstudianteAcudienteDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ActualizarEstudianteAcudienteDto dto, @MappingTarget EstudianteAcudienteEntity entity);
}
