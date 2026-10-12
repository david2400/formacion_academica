package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;
import com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.entity.ProfesorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfesorMapper {

    Profesor toDomain(ProfesorEntity entity);

    ProfesorEntity toEntity(Profesor domain);
}
