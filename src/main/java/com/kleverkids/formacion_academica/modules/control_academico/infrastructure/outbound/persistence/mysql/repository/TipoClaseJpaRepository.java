package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.TipoClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoClaseJpaRepository extends JpaRepository<TipoClaseEntity, Long> {

    Optional<TipoClaseEntity> findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);
}
