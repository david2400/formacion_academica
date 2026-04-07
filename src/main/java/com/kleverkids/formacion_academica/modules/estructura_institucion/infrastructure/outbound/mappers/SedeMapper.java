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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SedeMapper {

    // Entity a Domain Model
    @Mapping(target = "usrCrea", source = "usrCrea")
    @Mapping(target = "usrMod", source = "usrMod")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    Sede toDomainModel(SedeEntity entity);

    List<Sede> toDomainModelList(List<SedeEntity> entities);

    @Mapping(target = "eliminado", constant = "false")
    SedeEntity toEntity(CrearSedeDto dto);
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
