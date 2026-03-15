package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteJpaRepository extends JpaRepository<EstudianteEntity, Long> {

    boolean existsByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    Optional<EstudianteEntity> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento, String numeroDocumento);

    Optional<EstudianteEntity> findByIdAndActivoTrue(Long id);

    List<EstudianteEntity> findByActivoTrue();

    Page<EstudianteEntity> findByActivoTrue(Pageable pageable);
}
