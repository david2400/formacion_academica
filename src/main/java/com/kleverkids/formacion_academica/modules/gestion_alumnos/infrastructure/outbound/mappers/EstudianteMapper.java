package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.UpdateEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    EstudianteDto toDto(EstudianteEntity entity);

    List<EstudianteDto> toDtoList(List<EstudianteEntity> entities);

    default EstudianteEntity toEntity(CrearEstudianteDto dto) {
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(UUID.randomUUID());
        entity.setTipoDocumento(dto.tipoDocumento());
        entity.setNumeroDocumento(dto.numeroDocumento());
        entity.setNombres(dto.nombres());
        entity.setApellidos(dto.apellidos());
        entity.setFechaNacimiento(dto.fechaNacimiento());
        entity.setGenero(dto.genero());
        entity.setCorreo(dto.correo());
        entity.setTelefono(dto.telefono());
        entity.setDireccion(dto.direccion());
        entity.setActivo(true);
        return entity;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateEstudianteDto dto, @MappingTarget EstudianteEntity entity);

}
