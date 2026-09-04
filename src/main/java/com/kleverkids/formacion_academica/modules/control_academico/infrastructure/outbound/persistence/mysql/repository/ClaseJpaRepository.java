package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface ClaseJpaRepository extends JpaRepository<ClaseEntity, Long>,
        JpaSpecificationExecutor<ClaseEntity> {

    Optional<ClaseEntity> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
