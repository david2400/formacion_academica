package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.AulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaJpaRepository extends JpaRepository<AulaEntity, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}
