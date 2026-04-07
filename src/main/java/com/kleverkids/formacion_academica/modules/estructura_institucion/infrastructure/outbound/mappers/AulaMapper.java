package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;
import org.mapstruct.*;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AulaMapper {

    @Mapping(target = "usrCrea", source = "usrCrea")
    @Mapping(target = "usrMod", source = "usrMod")
    @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "instantToLocalDateTime")
    @Mapping(target = "eliminado", constant = "false")
    Aula toDomainModel(AulaEntity entity);

    List<Aula> toDomainModelList(List<AulaEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "grupos", ignore = true)
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eliminado", ignore = true)
    void updateEntityFromDomain(ActualizarAulaDto dto, @MappingTarget AulaEntity entity);

    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "eliminado", ignore = true)
    AulaEntity toEntity(CrearAulaDto dto);

    // Método de conversión de Instant a LocalDateTime
    @Named("instantToLocalDateTime")
    default LocalDateTime instantToLocalDateTime(Instant instant) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
