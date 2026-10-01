package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.ActivityDependencyRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityDependencyRuleJpaRepository extends JpaRepository<ActivityDependencyRuleEntity, Long> {

    List<ActivityDependencyRuleEntity> findByItemId(Long itemId);

    void deleteByItemIdAndId(Long itemId, Long id);

    void deleteByItemIdOrDependsOnItemId(Long itemId, Long dependsOnItemId);
}
