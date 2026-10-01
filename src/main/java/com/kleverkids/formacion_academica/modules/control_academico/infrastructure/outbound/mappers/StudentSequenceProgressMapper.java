package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.student_progress.StudentSequenceProgressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Mapeo completo en ambos sentidos, id incluido: {@code StudentSequenceProgressEntity}
 * no extiende AuditInfo (ver nota en la entidad), así que no hay campos
 * ocultos que el dominio pueda pisar con null al actualizar — es un upsert
 * directo (ver {@code StudentSequenceProgressJpaAdapter.guardar}).
 */
@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface StudentSequenceProgressMapper {

    StudentSequenceProgressEntity toEntity(StudentSequenceProgress progreso);

    StudentSequenceProgress toDomainModel(StudentSequenceProgressEntity entity);

    List<StudentSequenceProgress> toDomainModelList(List<StudentSequenceProgressEntity> entities);

    default Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    default LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
