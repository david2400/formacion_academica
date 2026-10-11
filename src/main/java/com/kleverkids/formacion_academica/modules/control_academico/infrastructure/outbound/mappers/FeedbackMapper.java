package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackCriterion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackForm;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackQuestionOption;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScale;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackScaleLevel;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.FeedbackSection;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.*;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Entidad ↔ dominio del módulo de feedback. Las respuestas y participaciones
 * se mapean a mano en su adapter (clave embebida y reglas de anonimato
 * explícitas, mejor visibles que generadas).
 *
 * <p>{@code eliminado} es de solo lectura en AuditInfo (lo gestiona
 * {@code @SoftDelete}); al ir hacia la entidad MapStruct lo copia pero
 * Hibernate lo ignora al insertar/actualizar.
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedbackMapper {

    FeedbackForm toDomain(FeedbackFormEntity entity);

    FeedbackFormEntity toEntity(FeedbackForm domain);

    List<FeedbackForm> toFormDomainList(List<FeedbackFormEntity> entities);

    FeedbackSection toDomain(FeedbackSectionEntity entity);

    FeedbackSectionEntity toEntity(FeedbackSection domain);

    List<FeedbackSection> toSectionDomainList(List<FeedbackSectionEntity> entities);

    FeedbackQuestion toDomain(FeedbackQuestionEntity entity);

    FeedbackQuestionEntity toEntity(FeedbackQuestion domain);

    List<FeedbackQuestion> toQuestionDomainList(List<FeedbackQuestionEntity> entities);

    FeedbackQuestionOption toDomain(FeedbackQuestionOptionEntity entity);

    FeedbackQuestionOptionEntity toEntity(FeedbackQuestionOption domain);

    List<FeedbackQuestionOption> toOptionDomainList(List<FeedbackQuestionOptionEntity> entities);

    FeedbackScale toDomain(FeedbackScaleEntity entity);

    FeedbackScaleEntity toEntity(FeedbackScale domain);

    FeedbackScaleLevel toDomain(FeedbackScaleLevelEntity entity);

    FeedbackScaleLevelEntity toEntity(FeedbackScaleLevel domain);

    List<FeedbackScaleLevel> toLevelDomainList(List<FeedbackScaleLevelEntity> entities);

    FeedbackCriterion toDomain(FeedbackCriterionEntity entity);

    FeedbackCriterionEntity toEntity(FeedbackCriterion domain);

    List<FeedbackCriterion> toCriterionDomainList(List<FeedbackCriterionEntity> entities);

    FeedbackAssignment toDomain(FeedbackAssignmentEntity entity);

    FeedbackAssignmentEntity toEntity(FeedbackAssignment domain);

    List<FeedbackAssignment> toAssignmentDomainList(List<FeedbackAssignmentEntity> entities);

    default LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    default Instant localDateTimeToInstant(LocalDateTime value) {
        return value == null ? null : value.atZone(ZoneId.systemDefault()).toInstant();
    }
}
