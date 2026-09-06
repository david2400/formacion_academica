package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoEstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoEstadoJpaRepository extends JpaRepository<CatalogoEstadoEntity, Long> {

    Optional<CatalogoEstadoEntity> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    List<CatalogoEstadoEntity> findAllByOrderByOrdenAsc();
}
