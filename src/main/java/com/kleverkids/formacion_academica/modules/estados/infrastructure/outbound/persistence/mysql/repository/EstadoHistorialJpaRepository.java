package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoHistorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EstadoHistorialJpaRepository extends JpaRepository<EstadoHistorialEntity, Long> {
    
    List<EstadoHistorialEntity> findByEntidadTipoAndEntidadId(String entidadTipo, Long entidadId);
    
    List<EstadoHistorialEntity> findByEntidadTipoAndEntidadIdOrderByCreatedAtDesc(String entidadTipo, Long entidadId);
    
    List<EstadoHistorialEntity> findByEstadoNuevoId(Long estadoNuevoId);
    
    List<EstadoHistorialEntity> findByEstadoAnteriorId(Long estadoAnteriorId);
    
    List<EstadoHistorialEntity> findByIdUsuarioCambio(Long idUsuarioCambio);
    
    List<EstadoHistorialEntity> findByIdEmpresa(Long idEmpresa);
    
    List<EstadoHistorialEntity> findByEntidadTipoAndIdEmpresa(String entidadTipo, Long idEmpresa);
    
    @Query("SELECT h FROM EstadoHistorialEntity h WHERE h.entidadTipo = :entidadTipo AND h.entidadId = :entidadId AND h.createdAt BETWEEN :fechaInicio AND :fechaFin ORDER BY h.createdAt DESC")
    List<EstadoHistorialEntity> findHistorialPorPeriodo(@Param("entidadTipo") String entidadTipo, @Param("entidadId") Long entidadId, 
                                                        @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);
    
    @Query("SELECT COUNT(h) FROM EstadoHistorialEntity h WHERE h.estadoNuevo.id = :estadoId AND h.createdAt >= :fecha")
    Long countCambiosEstadoDesde(@Param("estadoId") Long estadoId, @Param("fecha") LocalDateTime fecha);
    
    @Query("SELECT h FROM EstadoHistorialEntity h WHERE h.estadoNuevo.idModulo = :idModulo ORDER BY h.createdAt DESC")
    List<EstadoHistorialEntity> findHistorialPorModulo(@Param("idModulo") Long idModulo);
}
