package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.CatalogoEstadoContextoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CatalogoEstadoContextoJpaRepository extends JpaRepository<CatalogoEstadoContextoEntity, Long> {

    List<CatalogoEstadoContextoEntity> findByContextoIdAndIdEmpresaOrderByOrdenAsc(Long contextoId, Long idEmpresa);

    Optional<CatalogoEstadoContextoEntity> findFirstByContextoIdAndIdEmpresaAndEsInicialTrueOrderByOrdenAsc(
            Long contextoId, Long idEmpresa);

    Optional<CatalogoEstadoContextoEntity> findByContextoIdAndEstadoIdAndIdEmpresa(Long contextoId, Long estadoId,
            Long idEmpresa);

    boolean existsByContextoIdAndEstadoIdAndIdEmpresa(Long contextoId, Long estadoId, Long idEmpresa);

    /** Parametrizaciones de un estado: sirve para saber si está en uso. */
    List<CatalogoEstadoContextoEntity> findByEstadoId(Long estadoId);

    /** Deja un único estado inicial por contexto/empresa. */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE CatalogoEstadoContextoEntity p SET p.esInicial = false
            WHERE p.contextoId = :contextoId AND p.idEmpresa = :idEmpresa AND p.idParametrizacion <> :idExcluido
            """)
    int desmarcarInicialesSalvo(@Param("contextoId") Long contextoId,
            @Param("idEmpresa") Long idEmpresa,
            @Param("idExcluido") Long idExcluido);
}
