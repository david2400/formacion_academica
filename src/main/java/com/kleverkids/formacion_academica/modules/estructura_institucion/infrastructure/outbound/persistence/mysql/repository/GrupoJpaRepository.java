package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoJpaRepository extends JpaRepository<GrupoEntity, Long> {

    Optional<GrupoEntity> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
