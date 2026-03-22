package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.IntentoExamenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface IntentoExamenJpaRepository extends JpaRepository<IntentoExamenEntity, Long> {

    Optional<IntentoExamenEntity> findFirstByExamenIdAndEstudianteIdAndEstado(Long examenId, Long estudianteId, String estado);

    List<IntentoExamenEntity> findByExamenIdAndEstudianteIdOrderByIniciadoEnDesc(Long examenId, Long estudianteId);
}
