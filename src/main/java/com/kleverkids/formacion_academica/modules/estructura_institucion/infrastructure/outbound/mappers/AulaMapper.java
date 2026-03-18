package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AulaMapper {

    Aula toDomainModel(AulaEntity entity);

    List<Aula> toDomainModelList(List<AulaEntity> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "grupos", ignore = true)
    void updateEntityFromDomain(ActualizarAulaDto dto, @MappingTarget AulaEntity entity);

    default AulaEntity toEntity(CrearAulaDto dto) {
        AulaEntity entity = new AulaEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setCapacidad(dto.getCapacidad());
        // La relación grupos y propiedades de auditoría son manejadas por separado
        return entity;
    }
}
