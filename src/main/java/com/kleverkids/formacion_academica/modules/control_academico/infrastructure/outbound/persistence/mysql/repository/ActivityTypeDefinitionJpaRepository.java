package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityTypeDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActivityTypeDefinitionJpaRepository extends JpaRepository<ActivityTypeDefinitionEntity, Long> {

    Optional<ActivityTypeDefinitionEntity> findByType(String type);

    boolean existsByType(String type);

    List<ActivityTypeDefinitionEntity> findByActivoTrue();
}
