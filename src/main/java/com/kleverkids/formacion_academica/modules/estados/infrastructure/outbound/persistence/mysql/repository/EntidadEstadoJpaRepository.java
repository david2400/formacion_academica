package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntidadEstadoJpaRepository extends JpaRepository<EntidadEstadoEntity, Long> {
    
    Optional<EntidadEstadoEntity> findByUuid(String uuid);
    
    Optional<EntidadEstadoEntity> findByEntidadTipoAndEntidadIdAndActivoTrue(String entidadTipo, Long entidadId);
    
    List<EntidadEstadoEntity> findByEntidadTipoAndEntidadId(String entidadTipo, Long entidadId);
    
    List<EntidadEstadoEntity> findByEntidadTipoAndActivoTrue(String entidadTipo);
    
    List<EntidadEstadoEntity> findByEstadoId(Integer estadoId);
    
    List<EntidadEstadoEntity> findByEstadoIdAndActivoTrue(Integer estadoId);
    
    List<EntidadEstadoEntity> findByIdEmpresa(Long idEmpresa);
    
    List<EntidadEstadoEntity> findByEntidadTipoAndIdEmpresa(String entidadTipo, Long idEmpresa);
    
    List<EntidadEstadoEntity> findByEntidadTipoAndIdEmpresaAndActivoTrue(String entidadTipo, Long idEmpresa);
    
    @Query("SELECT ee FROM EntidadEstadoEntity ee WHERE ee.entidadTipo = :entidadTipo AND ee.entidadId = :entidadId AND ee.activo = true")
    Optional<EntidadEstadoEntity> findEstadoActual(@Param("entidadTipo") String entidadTipo, @Param("entidadId") Long entidadId);
    
    @Query("SELECT ee FROM EntidadEstadoEntity ee WHERE ee.estado.id = :estadoId AND ee.activo = true")
    List<EntidadEstadoEntity> findEntidadesConEstado(@Param("estadoId") Integer estadoId);
    
    @Query("SELECT COUNT(ee) FROM EntidadEstadoEntity ee WHERE ee.estado.id = :estadoId AND ee.activo = true")
    Long countEntidadesConEstado(@Param("estadoId") Integer estadoId);
    
    @Query("SELECT ee FROM EntidadEstadoEntity ee WHERE ee.entidadTipo = :entidadTipo AND ee.estado.idModulo = :idModulo AND ee.activo = true")
    List<EntidadEstadoEntity> findEntidadesPorTipoYModulo(@Param("entidadTipo") String entidadTipo, @Param("idModulo") Long idModulo);
    
    @Query("SELECT ee FROM EntidadEstadoEntity ee WHERE ee.entidadTipo = :entidadTipo AND ee.idEmpresa = :idEmpresa AND ee.activo = true ORDER BY ee.createdAt DESC")
    List<EntidadEstadoEntity> findEntidadesPorTipoYEmpresa(@Param("entidadTipo") String entidadTipo, @Param("idEmpresa") Long idEmpresa);
}
