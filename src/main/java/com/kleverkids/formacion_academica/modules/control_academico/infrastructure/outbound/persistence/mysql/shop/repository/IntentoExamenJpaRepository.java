package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.IntentoExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface IntentoExamenJpaRepository extends JpaRepository<IntentoExamenEntity, Long> {

    Optional<IntentoExamenEntity> findFirstByExamenIdAndEstudianteIdAndEstado(Long examenId, Long estudianteId, String estado);

    List<IntentoExamenEntity> findByExamenIdAndEstudianteIdOrderByIniciadoEnDesc(Long examenId, Long estudianteId);
}
