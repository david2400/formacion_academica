package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;

import java.util.List;
import java.util.UUID;

public final class AcudienteMapper {

    private AcudienteMapper() {
    }

    public static AcudienteEntity toEntity(CrearAcudienteDto dto) {
        AcudienteEntity entity = new AcudienteEntity();
        entity.setId(UUID.randomUUID());
        entity.setEstudianteId(dto.estudianteId());
        entity.setTipoDocumento(dto.tipoDocumento());
        entity.setNumeroDocumento(dto.numeroDocumento());
        entity.setNombres(dto.nombres());
        entity.setApellidos(dto.apellidos());
        entity.setParentesco(dto.parentesco());
        entity.setTelefono(dto.telefono());
        entity.setCorreo(dto.correo());
        entity.setEsPrincipal(dto.esPrincipal());
        entity.setActivo(true);
        return entity;
    }

    public static void merge(AcudienteEntity entity, ActualizarAcudienteDto dto) {
        if (dto.estudianteId() != null) {
            entity.setEstudianteId(dto.estudianteId());
        }
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
        if (dto.parentesco() != null) {
            entity.setParentesco(dto.parentesco());
        }
        if (dto.telefono() != null) {
            entity.setTelefono(dto.telefono());
        }
        if (dto.correo() != null) {
            entity.setCorreo(dto.correo());
        }
        if (dto.esPrincipal() != null) {
            entity.setEsPrincipal(dto.esPrincipal());
        }
    }

    public static AcudienteDto toDto(AcudienteEntity entity) {
        return new AcudienteDto(
                entity.getId(),
                entity.getEstudianteId(),
                entity.getTipoDocumento(),
                entity.getNumeroDocumento(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getParentesco(),
                entity.getTelefono(),
                entity.getCorreo(),
                entity.isEsPrincipal(),
                entity.isActivo()
        );
    }

    public static List<AcudienteDto> toDtoList(List<AcudienteEntity> entities) {
        return entities.stream().map(AcudienteMapper::toDto).toList();
    }
}
