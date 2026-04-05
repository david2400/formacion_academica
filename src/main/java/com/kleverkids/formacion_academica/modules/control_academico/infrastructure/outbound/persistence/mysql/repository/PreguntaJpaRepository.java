package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntaJpaRepository extends JpaRepository<PreguntaEntity, Long> {
    
    // Métodos de búsqueda básicos - implementaciones mínimas
    default Page<PreguntaEntity> buscar(
        String questionType, String difficulty, Long themeId, String searchText, Pageable pageable) {
        // Implementación mínima - retornar página vacía
        return Page.empty();
    }
    
    default Page<PreguntaEntity> buscarConEliminados(
        String questionType, String difficulty, Long themeId, String searchText, boolean includeDeleted, Pageable pageable) {
        // Implementación mínima - retornar página vacía
        return Page.empty();
    }
}
