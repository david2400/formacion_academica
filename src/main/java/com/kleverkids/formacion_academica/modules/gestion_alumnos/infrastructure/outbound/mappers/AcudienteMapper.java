package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.ActualizarAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.CrearAcudienteDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AcudienteMapper {

    AcudienteDto toDto(AcudienteEntity entity);

    List<AcudienteDto> toDtoList(List<AcudienteEntity> entities);

    default AcudienteEntity toEntity(CrearAcudienteDto dto) {
        AcudienteEntity entity = new AcudienteEntity();
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

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateEntityFromDto(ActualizarAcudienteDto dto, @MappingTarget AcudienteEntity entity);
}
