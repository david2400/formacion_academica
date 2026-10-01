package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.CrearLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceVersion;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.ActivityDependencyRuleEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceItemEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceVersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface LearningSequenceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", constant = "DRAFT")
    @Mapping(target = "versionActualId", ignore = true)
    @Mapping(target = "eliminado", constant = "false")
    @Mapping(target = "usrCrea", ignore = true)
    @Mapping(target = "usrMod", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    LearningSequenceEntity toEntity(CrearLearningSequenceDto dto);

    LearningSequence toDomainModel(LearningSequenceEntity entity);

    List<LearningSequence> toDomainModelList(List<LearningSequenceEntity> entities);

    LearningSequenceItem toDomainModel(LearningSequenceItemEntity entity);

    List<LearningSequenceItem> toItemDomainModelList(List<LearningSequenceItemEntity> entities);

    LearningSequenceVersion toDomainModel(LearningSequenceVersionEntity entity);

    @Mapping(target = "createdAt", source = "createdAt")
    ActivityDependencyRule toDomainModel(ActivityDependencyRuleEntity entity);

    List<ActivityDependencyRule> toDependencyDomainModelList(List<ActivityDependencyRuleEntity> entities);

    default LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
