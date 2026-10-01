package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.CrearActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityVersion;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityContentItemEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActivityMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "DRAFT")
    @Mapping(target = "esObligatoria", ignore = true)
    @Mapping(target = "permiteReintentos", ignore = true)
    @Mapping(target = "versionActualId", ignore = true)
    @Mapping(target = "eliminado", constant = "false")
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ActivityEntity toEntity(CrearActivityDto dto);

    Activity toDomainModel(ActivityEntity entity);

    List<Activity> toDomainModelList(List<ActivityEntity> entities);

    ActivityContentItem toDomainModel(ActivityContentItemEntity entity);

    List<ActivityContentItem> toContentDomainModelList(List<ActivityContentItemEntity> entities);

    ActivityVersion toDomainModel(ActivityVersionEntity entity);

    default LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
