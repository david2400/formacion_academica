package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.NivelEducativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NivelEducativoJpaRepository extends JpaRepository<NivelEducativoEntity, Long> {
    
    Optional<NivelEducativoEntity> findByCodigo(String codigo);
    
    // List<NivelEducativoEntity> findByCategoria(String categoria);
    
    List<NivelEducativoEntity> findByNivelSuperiorId(Long nivelSuperiorId);
    
    List<NivelEducativoEntity> findByEliminadoFalse();

    
    // List<NivelEducativoEntity> findByCategoriaAndEliminadoFalse(String categoria);
    
    boolean existsByCodigo(String codigo);
    
    @Query("SELECT n FROM NivelEducativoEntity n WHERE n.eliminado = true")
    List<NivelEducativoEntity> findActivosOrdenados();
    
    // @Query("SELECT n FROM NivelEducativoEntity n WHERE n.categoria = :categoria AND n.eliminado = true")
    // List<NivelEducativoEntity> findPorCategoriaActivosOrdenados(@Param("categoria") String categoria);
    
    @Query("SELECT n FROM NivelEducativoEntity n WHERE n.nivelSuperior IS NULL AND n.eliminado = true")
    List<NivelEducativoEntity> findNivelesPrincipalesActivos();
}
