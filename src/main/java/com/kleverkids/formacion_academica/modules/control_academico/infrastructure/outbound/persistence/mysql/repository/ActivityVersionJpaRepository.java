package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityVersionJpaRepository extends JpaRepository<ActivityVersionEntity, Long> {

    int countByActivityId(Long activityId);
}
