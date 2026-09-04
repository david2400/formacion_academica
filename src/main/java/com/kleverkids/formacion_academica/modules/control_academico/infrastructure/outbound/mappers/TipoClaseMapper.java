package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.CrearTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.TipoClase;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.TipoClaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TipoClaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eliminado", constant = "false")
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TipoClaseEntity toEntity(CrearTipoClaseDto dto);

    @Mapping(target = "usrCrea", source = "usrCrea")
    @Mapping(target = "usrMod", source = "usrMod")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    TipoClase toDomainModel(TipoClaseEntity entity);

    List<TipoClase> toDomainModelList(List<TipoClaseEntity> entities);

    // Conversión de Instant (AuditInfo) a LocalDateTime (modelo de dominio).
    default LocalDateTime instantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
