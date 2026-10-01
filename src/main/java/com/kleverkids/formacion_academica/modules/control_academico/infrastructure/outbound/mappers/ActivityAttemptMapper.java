package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt.ActivityAttemptAnswerEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt.ActivityAttemptEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * {@code toEntity(ActivityAttempt)} solo se usa para crear (id/version
 * ignorados: un intento nuevo nunca llega aquí con id). Las actualizaciones
 * (completar un intento) se hacen en el adapter sobre la entidad ya
 * existente, para no pisar con null los campos de auditoría de
 * {@code AuditInfo} que el dominio {@code ActivityAttempt} no conoce (ver
 * nota en {@code ActivityAttemptJpaAdapter.guardar}).
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActivityAttemptMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "eliminado", constant = "false")
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ActivityAttemptEntity toEntity(ActivityAttempt attempt);

    ActivityAttempt toDomainModel(ActivityAttemptEntity entity);

    List<ActivityAttempt> toDomainModelList(List<ActivityAttemptEntity> entities);

    ActivityAttemptAnswerEntity toEntity(ActivityAttemptAnswer answer);

    ActivityAttemptAnswer toDomainModel(ActivityAttemptAnswerEntity entity);

    List<ActivityAttemptAnswer> toAnswerDomainModelList(List<ActivityAttemptAnswerEntity> entities);

    default Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    default LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
