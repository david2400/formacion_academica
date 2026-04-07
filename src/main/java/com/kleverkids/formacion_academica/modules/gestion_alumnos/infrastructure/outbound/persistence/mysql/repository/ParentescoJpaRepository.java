package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.ParentescoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParentescoJpaRepository extends JpaRepository<ParentescoEntity, Long> {

    @Query("SELECT p FROM ParentescoEntity p WHERE p.eliminado = true")
    List<ParentescoEntity> findByEliminadoFalse();

    @Query("SELECT p FROM ParentescoEntity p WHERE p.nombre = :nombre")
    Optional<ParentescoEntity> findByNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM ParentescoEntity p WHERE p.nombre = :nombre AND p.id != :id")
    Optional<ParentescoEntity> findByNombreAndIdNot(@Param("nombre") String nombre, @Param("id") Long id);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, Long id);
}
