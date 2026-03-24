package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcudienteJpaRepository extends JpaRepository<AcudienteEntity, Long> {

    @Query("SELECT a FROM AcudienteEntity a " +
           "JOIN EstudianteAcudienteEntity ea ON a.id = ea.acudienteId " +
           "WHERE ea.estudianteId = :estudianteId")
    List<AcudienteEntity> findByEstudianteId(@Param("estudianteId") Long estudianteId);

    @Query("SELECT CASE WHEN COUNT(ea) > 0 THEN true ELSE false END " +
           "FROM EstudianteAcudienteEntity ea " +
           "JOIN AcudienteEntity a ON ea.acudienteId = a.id " +
           "WHERE ea.estudianteId = :estudianteId AND a.esPrincipal = true " +
           "AND (:excluirId IS NULL OR a.id != :excluirId)")
    boolean existsByEstudianteIdAndEsPrincipalIsTrue(@Param("estudianteId") Long estudianteId, 
                                                     @Param("excluirId") Long excluirId);
}
