package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.ExamenEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamenJpaRepository extends JpaRepository<ExamenEntity, Long> {

    // Usado por ExamenJpaAdapter#searchExams para el filtro searchText del
    // listado GET /examenes -el único que hoy consulta datos reales, ver
    // comentario ahí.
    Page<ExamenEntity> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Usado por ExamenJpaAdapter#searchExams para el filtro tematicaId: pagina
    // sobre el subconjunto de examenId que ya se resolvió vía
    // ExamenTematicaJpaRepository#findByTematicaId.
    Page<ExamenEntity> findByIdIn(List<Long> ids, Pageable pageable);
}
