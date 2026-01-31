package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.IntentoExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IntentoExamenJpaRepository extends JpaRepository<IntentoExamenEntity, UUID> {

    Optional<IntentoExamenEntity> findFirstByExamenIdAndEstudianteIdAndEstado(UUID examenId, UUID estudianteId, String estado);

    List<IntentoExamenEntity> findByExamenIdAndEstudianteIdOrderByIniciadoEnDesc(UUID examenId, UUID estudianteId);
}
