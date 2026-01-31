package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.ActualizarEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.CrearEstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante.EstudianteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;

import java.util.List;
import java.util.UUID;

public final class EstudianteMapper {

    private EstudianteMapper() {
    }

    public static EstudianteEntity toEntity(CrearEstudianteDto dto) {
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

    public static void merge(EstudianteEntity entity, ActualizarEstudianteDto dto) {
        if (dto.tipoDocumento() != null) {
            entity.setTipoDocumento(dto.tipoDocumento());
        }
        if (dto.numeroDocumento() != null) {
            entity.setNumeroDocumento(dto.numeroDocumento());
        }
        if (dto.nombres() != null) {
            entity.setNombres(dto.nombres());
        }
        if (dto.apellidos() != null) {
            entity.setApellidos(dto.apellidos());
        }
        if (dto.fechaNacimiento() != null) {
            entity.setFechaNacimiento(dto.fechaNacimiento());
        }
        if (dto.genero() != null) {
            entity.setGenero(dto.genero());
        }
        if (dto.correo() != null) {
            entity.setCorreo(dto.correo());
        }
        if (dto.telefono() != null) {
            entity.setTelefono(dto.telefono());
        }
        if (dto.direccion() != null) {
            entity.setDireccion(dto.direccion());
        }
    }

    public static EstudianteDto toDto(EstudianteEntity entity) {
        return new EstudianteDto(
                entity.getId(),
                entity.getTipoDocumento(),
                entity.getNumeroDocumento(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getFechaNacimiento(),
                entity.getGenero(),
                entity.getCorreo(),
                entity.getTelefono(),
                entity.getDireccion(),
                entity.isActivo()
        );
    }

    public static List<EstudianteDto> toDtoList(List<EstudianteEntity> entities) {
        return entities.stream().map(EstudianteMapper::toDto).toList();
    }
}
