package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteGrupoJpaRepository extends JpaRepository<EstudianteGrupoEntity, Long> {

    List<EstudianteGrupoEntity> findByGrupoIdOrderByIdAsc(Long grupoId);

    List<EstudianteGrupoEntity> findByEstudianteIdOrderByIdAsc(Long estudianteId);

    Optional<EstudianteGrupoEntity> findByEstudianteIdAndGrupoId(Long estudianteId, Long grupoId);

    /**
     * Busca ignorando el borrado lógico.
     *
     * <p>{@code AuditInfo} lleva {@code @SoftDelete}: al remover un estudiante la fila
     * queda marcada y desaparece de toda consulta JPQL, pero sigue ocupando la clave
     * única {@code (estudiante_id, grupo_id)}. Volver a asignarlo por INSERT fallaría,
     * así que hay que localizar la fila previa y revivirla. Solo una consulta nativa
     * la ve.
     */
    @Query(value = """
            SELECT * FROM estudiantes_grupo
            WHERE estudiante_id = :estudianteId AND grupo_id = :grupoId
            LIMIT 1
            """, nativeQuery = true)
    Optional<EstudianteGrupoEntity> buscarIncluyendoEliminados(@Param("estudianteId") Long estudianteId,
            @Param("grupoId") Long grupoId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = """
            UPDATE estudiantes_grupo
            SET eliminado = false, estado_id = :estadoId, fecha_asignacion = :fechaAsignacion
            WHERE id = :id
            """, nativeQuery = true)
    int reactivar(@Param("id") Long id,
            @Param("estadoId") Long estadoId,
            @Param("fechaAsignacion") LocalDate fechaAsignacion);
}
