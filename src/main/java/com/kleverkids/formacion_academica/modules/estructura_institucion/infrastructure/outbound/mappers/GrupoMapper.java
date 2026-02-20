package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import com.kleverkids.formacion_academica.shared.common.domain.dto.AuditInfoDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrupoMapper {

    @Mapping(target = "audit", source = "entity", qualifiedByName = "toAuditDto")
    GrupoDto toDto(GrupoEntity entity);

    List<GrupoDto> toDtoList(List<GrupoEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ActualizarGrupoDto dto, @MappingTarget GrupoEntity entity);

    default GrupoEntity toEntity(CrearGrupoDto dto) {
        GrupoEntity entity = new GrupoEntity();
        entity.setId(UUID.randomUUID());
        entity.setCodigo(dto.codigo());
        entity.setNombre(dto.nombre());
        entity.setGradoId(dto.gradoId());
        entity.setCapacidadMaxima(dto.capacidadMaxima());
        entity.setTutorId(dto.tutorId());
        entity.setAulaId(dto.aulaId());
        entity.setActivo(true);
        return entity;
    }


    @Named("toAuditDto")
    default AuditInfoDto toAuditDto(GrupoEntity entity) {
        if (entity == null) {
            return null;
        }
        return AuditInfoDto.builder()
                .usrCrea(entity.getUsrCrea())
                .usrMod(entity.getUsrMod())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .deleted(entity.isDeleted())
                .build();
    }
}
