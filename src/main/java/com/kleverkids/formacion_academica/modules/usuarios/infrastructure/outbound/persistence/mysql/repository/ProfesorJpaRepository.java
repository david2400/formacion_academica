package com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.entity.ProfesorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface ProfesorJpaRepository extends JpaRepository<ProfesorEntity, Long> {

    /** Solo filas vigentes: @SoftDelete agrega "eliminado = false". */
    boolean existsByUsuarioIdAndEmpresaId(Long usuarioId, Long empresaId);

    /** SQL nativo porque @SoftDelete oculta las filas eliminadas a JPQL. */
    @Query(value = "SELECT id FROM profesores WHERE usuario_id = :usuarioId AND empresa_id = :empresaId AND eliminado = 1",
            nativeQuery = true)
    Optional<Long> idEliminado(@Param("usuarioId") Long usuarioId, @Param("empresaId") Long empresaId);

    @Modifying
    @Query(value = "UPDATE profesores SET eliminado = 0, activo = 1, fecha_vinculacion = :fecha, observaciones = :obs, "
            + "usr_mod = 1, updated_at = CURRENT_TIMESTAMP WHERE id = :id", nativeQuery = true)
    int restaurar(@Param("id") Long id, @Param("fecha") LocalDate fecha, @Param("obs") String observaciones);
}
