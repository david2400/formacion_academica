package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SalonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SalonJpaRepository extends JpaRepository<SalonEntity, Long> {
    
    Optional<SalonEntity> findByCodigo(String codigo);
    
    List<SalonEntity> findAllByOrderByNombreAsc();
    
    boolean existsByCodigo(String codigo);
}
