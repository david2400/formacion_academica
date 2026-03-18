package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstadoJpaRepository extends JpaRepository<EstadoEntity, Long> {
    
    Optional<EstadoEntity> findByUuid(String uuid);
    
    Optional<EstadoEntity> findByCodigo(String codigo);
    
    Optional<EstadoEntity> findByCodigoAndIdModulo(String codigo, Long idModulo);
    
    List<EstadoEntity> findByIdModulo(Long idModulo);
    
    List<EstadoEntity> findByIdModuloAndActivoTrue(Long idModulo);
    
    List<EstadoEntity> findByIdModuloAndActivoTrueOrderByOrdenAsc(Long idModulo);
    
    List<EstadoEntity> findByIdModuloAndEsInicialTrue(Long idModulo);
    
    List<EstadoEntity> findByIdModuloAndEsFinalTrue(Long idModulo);
    
    List<EstadoEntity> findByIdModuloAndIdEmpresa(Long idModulo, Long idEmpresa);
    
    List<EstadoEntity> findByIdModuloAndIdEmpresaAndActivoTrue(Long idModulo, Long idEmpresa);
    
    boolean existsByCodigoAndIdModulo(String codigo, Long idModulo);
    
    boolean existsByCodigoAndIdModuloAndIdEmpresa(String codigo, Long idModulo, Long idEmpresa);
    
    @Query("SELECT e FROM EstadoEntity e WHERE e.idModulo = :idModulo AND e.activo = true ORDER BY e.orden ASC")
    List<EstadoEntity> findEstadosActivosPorModulo(@Param("idModulo") Long idModulo);
    
    @Query("SELECT e FROM EstadoEntity e WHERE e.idModulo = :idModulo AND e.esInicial = true AND e.activo = true")
    List<EstadoEntity> findEstadosInicialesPorModulo(@Param("idModulo") Long idModulo);
    
    @Query("SELECT e FROM EstadoEntity e WHERE e.idModulo = :idModulo AND e.esFinal = true AND e.activo = true")
    List<EstadoEntity> findEstadosFinalesPorModulo(@Param("idModulo") Long idModulo);
    
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM EstadoEntity e WHERE e.codigo = :codigo AND e.idModulo = :idModulo AND e.activo = true")
    boolean existsByCodigoAndIdModuloAndActivoTrue(@Param("codigo") String codigo, @Param("idModulo") Long idModulo);
}
