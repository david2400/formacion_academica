package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.ActualizarEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.CrearEstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente.EstudianteAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface RelacionEstudianteAcudienteMapper {

    EstudianteAcudienteDto toDto(EstudianteAcudienteEntity entity);

    List<EstudianteAcudienteDto> toDtoList(List<EstudianteAcudienteEntity> entities);

    default EstudianteAcudienteEntity toEntity(CrearEstudianteAcudienteDto dto) {
        EstudianteAcudienteEntity entity = new EstudianteAcudienteEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteId(dto.estudianteId());
        entity.setAcudienteId(dto.acudienteId());
        entity.setParentesco(dto.parentesco());
        entity.setEsPrincipal(dto.esPrincipal());
        entity.setEstado("ACTIVA");
        entity.setFechaVinculacion(LocalDate.now());
        return entity;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ActualizarEstudianteAcudienteDto dto, @MappingTarget EstudianteAcudienteEntity entity);
}
