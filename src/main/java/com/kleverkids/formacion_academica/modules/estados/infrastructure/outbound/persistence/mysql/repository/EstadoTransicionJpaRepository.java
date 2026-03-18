package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoTransicionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoTransicionJpaRepository extends JpaRepository<EstadoTransicionEntity, Long> {
    
    Optional<EstadoTransicionEntity> findByUuid(String uuid);
    
    List<EstadoTransicionEntity> findByIdModulo(Long idModulo);
    
    List<EstadoTransicionEntity> findByIdModuloAndActivaTrue(Long idModulo);
    
    List<EstadoTransicionEntity> findByEstadoOrigenIdAndIdModuloAndActivaTrue(Long estadoOrigenId, Long idModulo);
    
    List<EstadoTransicionEntity> findByEstadoDestinoIdAndIdModuloAndActivaTrue(Long estadoDestinoId, Long idModulo);
    
    Optional<EstadoTransicionEntity> findByEstadoOrigenIdAndEstadoDestinoIdAndIdModuloAndActivaTrue(
        Long estadoOrigenId, Long estadoDestinoId, Long idModulo);
    
    List<EstadoTransicionEntity> findByIdModuloAndIdEmpresa(Long idModulo, Long idEmpresa);
    
    List<EstadoTransicionEntity> findByIdModuloAndIdEmpresaAndActivaTrue(Long idModulo, Long idEmpresa);
    
    @Query("SELECT t FROM EstadoTransicionEntity t WHERE t.estadoOrigen.id = :estadoOrigenId AND t.idModulo = :idModulo AND t.activa = true ORDER BY t.orden ASC")
    List<EstadoTransicionEntity> findTransicionesDesdeEstado(@Param("estadoOrigenId") Long estadoOrigenId, @Param("idModulo") Long idModulo);
    
    @Query("SELECT t FROM EstadoTransicionEntity t WHERE t.estadoDestino.id = :estadoDestinoId AND t.idModulo = :idModulo AND t.activa = true ORDER BY t.orden ASC")
    List<EstadoTransicionEntity> findTransicionesHaciaEstado(@Param("estadoDestinoId") Long estadoDestinoId, @Param("idModulo") Long idModulo);
    
    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM EstadoTransicionEntity t WHERE t.estadoOrigen.id = :estadoOrigenId AND t.estadoDestino.id = :estadoDestinoId AND t.idModulo = :idModulo AND t.activa = true")
    boolean existeTransicionValida(@Param("estadoOrigenId") Long estadoOrigenId, @Param("estadoDestinoId") Long estadoDestinoId, @Param("idModulo") Long idModulo);
    
    @Query("SELECT t FROM EstadoTransicionEntity t WHERE t.estadoOrigen.id = :estadoOrigenId AND t.estadoDestino.id = :estadoDestinoId AND t.idModulo = :idModulo AND t.idEmpresa = :idEmpresa AND t.activa = true")
    Optional<EstadoTransicionEntity> findTransicionPorEmpresa(@Param("estadoOrigenId") Long estadoOrigenId, @Param("estadoDestinoId") Long estadoDestinoId, @Param("idModulo") Long idModulo, @Param("idEmpresa") Long idEmpresa);
}
