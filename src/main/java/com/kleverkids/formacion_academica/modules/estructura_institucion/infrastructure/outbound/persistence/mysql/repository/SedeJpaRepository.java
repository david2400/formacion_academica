package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SedeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SedeJpaRepository extends JpaRepository<SedeEntity, Long> {

//    Optional<SedeEntity> findByCodigo(String codigo);

//    boolean existsByCodigo(String codigo);

    List<SedeEntity> findByEliminadoFalse();

    List<SedeEntity> findByCiudadIdContainingIgnoreCase(String ciudad);

//    @Query("SELECT s FROM SedeEntity s WHERE s.eliminado = true AND s.ciudad LIKE %:ciudad%")
//    List<SedeEntity> findActivasByCiudad(@Param("ciudad") String ciudad);
}
