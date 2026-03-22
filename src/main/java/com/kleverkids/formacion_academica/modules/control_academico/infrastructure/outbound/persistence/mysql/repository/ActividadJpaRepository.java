package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.ActividadEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadJpaRepository extends JpaRepository<ActividadEntity, Long> {

    // Búsquedas básicas
    List<ActividadEntity> findByCursoIdOrderByOrdenAsc(Long cursoId);
    List<ActividadEntity> findByModuloIdOrderByOrdenAsc(Long moduloId);
    List<ActividadEntity> findByTipo(ActividadEntity.TipoActividadEntity tipo);
    List<ActividadEntity> findByEstado(ActividadEntity.EstadoActividadEntity estado);

    // Búsquedas con filtros combinados
    @Query("SELECT a FROM ActividadEntity a WHERE " +
           "(:cursoId IS NULL OR a.cursoId = :cursoId) AND " +
           "(:moduloId IS NULL OR a.moduloId = :moduloId) AND " +
           "(:tipo IS NULL OR a.tipo = :tipo) AND " +
           "(:estado IS NULL OR a.estado = :estado) AND " +
           "(:titulo IS NULL OR LOWER(a.titulo) LIKE LOWER(CONCAT('%', :titulo, '%')))")
    Page<ActividadEntity> buscarConFiltros(
        @Param("cursoId") Long cursoId,
        @Param("moduloId") Long moduloId,
        @Param("tipo") ActividadEntity.TipoActividadEntity tipo,
        @Param("estado") ActividadEntity.EstadoActividadEntity estado,
        @Param("titulo") String titulo,
        Pageable pageable
    );

    // Búsquedas por fechas
    List<ActividadEntity> findByFechaInicioBetween(LocalDateTime inicio, LocalDateTime fin);
    List<ActividadEntity> findByFechaFinBetween(LocalDateTime inicio, LocalDateTime fin);

    // Actividades disponibles para estudiantes
    @Query("SELECT a FROM ActividadEntity a WHERE a.estado = 'ACTIVA' AND " +
           "(a.fechaInicio IS NULL OR a.fechaInicio <= :fechaActual) AND " +
           "(a.fechaFin IS NULL OR a.fechaFin >= :fechaActual)")
    List<ActividadEntity> findActividadesDisponibles(@Param("fechaActual") LocalDateTime fechaActual);

    // Búsquedas por dependencias
    @Query("SELECT a FROM ActividadEntity a JOIN a.actividadesDependientes dep WHERE dep = :actividadId")
    List<ActividadEntity> findActividadesQueDependen(@Param("actividadId") Long actividadId);

    // Validaciones
    boolean existsByTituloAndCursoId(String titulo, Long cursoId);
    
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM ActividadEntity a " +
           "WHERE a.id = :actividadId AND :dependenciaId IN (SELECT dep FROM a.actividadesDependientes dep)")
    boolean existeDependenciaDirecta(@Param("actividadId") Long actividadId, @Param("dependenciaId") Long dependenciaId);

    // Estadísticas
    @Query("SELECT COUNT(a) FROM ActividadEntity a WHERE a.cursoId = :cursoId")
    Long contarPorCurso(@Param("cursoId") Long cursoId);

    @Query("SELECT COUNT(a) FROM ActividadEntity a WHERE a.cursoId = :cursoId AND a.estado = 'ACTIVA'")
    Long contarActivasPorCurso(@Param("cursoId") Long cursoId);

    // Ordenamiento
    @Query("SELECT a FROM ActividadEntity a WHERE a.cursoId = :cursoId AND " +
           "(:moduloId IS NULL OR a.moduloId = :moduloId) ORDER BY a.orden ASC")
    List<ActividadEntity> listarPorOrden(@Param("cursoId") Long cursoId, @Param("moduloId") Long moduloId);

    // Búsqueda de actividades que requieren evaluación
    @Query("SELECT a FROM ActividadEntity a WHERE a.evaluacion IS NOT NULL")
    List<ActividadEntity> findByEvaluacionRequerida();

    // Búsqueda de actividades que un estudiante puede realizar
    // Simplificado - sin referencia a ProgresoActividadEntity que no existe
    @Query("SELECT a FROM ActividadEntity a WHERE " +
           "a.estado = 'ACTIVA' AND " +
           "(a.fechaInicio IS NULL OR a.fechaInicio <= :fechaActual) AND " +
           "(a.fechaFin IS NULL OR a.fechaFin >= :fechaActual)")
    List<ActividadEntity> findActividadesDisponiblesParaEstudiante(
        @Param("estudianteId") Long estudianteId,
        @Param("fechaActual") LocalDateTime fechaActual
    );

    // Búsqueda por texto en título y descripción
    @Query("SELECT a FROM ActividadEntity a WHERE " +
           "LOWER(a.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) OR " +
           "LOWER(a.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<ActividadEntity> buscarPorTexto(@Param("texto") String texto);
}
