package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaRepository extends JpaRepository<PreguntaEntity, Long> {
    
    List<PreguntaEntity> findByTemaId(Long temaId);

    // Usado cuando una actividad tiene varias temáticas asignadas (ver
    // ActivityTematicaEntity): el banco de preguntas se filtra por el
    // conjunto, no por una sola, a diferencia de findByTemaId.
    List<PreguntaEntity> findByTemaIdIn(java.util.List<Long> temaIds);
    
    @Query("SELECT p FROM PreguntaEntity p WHERE p.dificultad = :dificultad")
    List<PreguntaEntity> findByDificultad(@Param("dificultad") String dificultad);
    
    @Query(value = "SELECT * FROM preguntas p WHERE JSON_CONTAINS(p.tags, JSON_QUOTE(:tag))", nativeQuery = true)
    List<PreguntaEntity> findByTag(@Param("tag") String tag);
}
