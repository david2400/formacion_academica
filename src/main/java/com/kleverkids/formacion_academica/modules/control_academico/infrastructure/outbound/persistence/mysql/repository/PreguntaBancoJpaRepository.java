package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaBancoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreguntaBancoJpaRepository extends JpaRepository<PreguntaBancoEntity, Long> {

    List<PreguntaBancoEntity> findByTematicaId(Long tematicaId);
}
