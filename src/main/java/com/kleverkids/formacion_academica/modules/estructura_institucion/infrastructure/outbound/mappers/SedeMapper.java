package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.ActualizarSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.CrearSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SedeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SedeMapper {

    // Entity a Domain Model
    @Mapping(target = "activo", source = "activo")
    @Mapping(target = "usrCrea", source = "usrCrea")
    @Mapping(target = "usrMod", source = "usrMod")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    Sede toDomainModel(SedeEntity entity);

    List<Sede> toDomainModelList(List<SedeEntity> entities);

    // DTO a Entity - método manual para evitar conflictos con AuditInfo
    default SedeEntity toEntity(CrearSedeDto dto) {
        SedeEntity entity = new SedeEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setDireccion(dto.getDireccion());
        entity.setCiudadId(dto.getCiudadId());
        entity.setDepartamentoId(dto.getDepartamentoId());
        entity.setPais(dto.getPais());
        entity.setTelefono(dto.getTelefono());
        entity.setEmail(dto.getEmail());
        entity.setContactoPrincipal(dto.getContactoPrincipal());
        entity.setTelefonoContacto(dto.getTelefonoContacto());
        entity.setEmailContacto(dto.getEmailContacto());
        return entity;
    }

    // Update partial
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "salones", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDto(ActualizarSedeDto dto, @MappingTarget SedeEntity entity);

    @Named("instantToLocalDateTime")
    default LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, ZoneId.systemDefault()) : null;
    }
}
