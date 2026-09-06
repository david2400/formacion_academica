package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoContextoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoContextoJpaRepository extends JpaRepository<CatalogoContextoEntity, Long> {

    Optional<CatalogoContextoEntity> findByCodigo(String codigo);

    Optional<CatalogoContextoEntity> findByModuloAndEntidad(String modulo, String entidad);

    List<CatalogoContextoEntity> findAllByOrderByCodigoAsc();
}
