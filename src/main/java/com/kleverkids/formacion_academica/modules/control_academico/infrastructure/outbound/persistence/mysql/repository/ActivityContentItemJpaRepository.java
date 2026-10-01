package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityContentItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityContentItemJpaRepository extends JpaRepository<ActivityContentItemEntity, Long> {

    List<ActivityContentItemEntity> findByActivityIdOrderByOrdenAsc(Long activityId);

    int countByActivityId(Long activityId);

    void deleteByActivityIdAndId(Long activityId, Long id);
}
