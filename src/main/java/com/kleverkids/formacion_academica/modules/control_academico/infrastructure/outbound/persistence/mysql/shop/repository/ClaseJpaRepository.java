package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClaseJpaRepository extends JpaRepository<ClaseEntity, UUID> {

    Optional<ClaseEntity> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
