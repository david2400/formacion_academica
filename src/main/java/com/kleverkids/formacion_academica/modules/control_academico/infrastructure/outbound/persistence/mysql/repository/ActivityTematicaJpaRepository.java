package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityTematicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityTematicaJpaRepository extends JpaRepository<ActivityTematicaEntity, Long> {

    List<ActivityTematicaEntity> findByActivityId(Long activityId);

    Optional<ActivityTematicaEntity> findByActivityIdAndTematicaId(Long activityId, Long tematicaId);

    boolean existsByActivityIdAndTematicaId(Long activityId, Long tematicaId);

    void deleteByActivityIdAndTematicaId(Long activityId, Long tematicaId);
}
