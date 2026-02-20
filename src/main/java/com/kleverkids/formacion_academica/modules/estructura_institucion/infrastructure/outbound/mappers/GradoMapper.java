package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface GradoMapper {

    GradoDto toDto(GradoEntity entity);

    default GradoEntity toEntity(CrearGradoDto dto) {
        GradoEntity entity = new GradoEntity();
        entity.setId(UUID.randomUUID());
        entity.setNombre(dto.getNombre());
        entity.setNivelEducativo(dto.getNivelEducativo());
        entity.setOrden(dto.getOrden());
        return entity;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ActualizarGradoDto dto, @MappingTarget GradoEntity entity);
}
