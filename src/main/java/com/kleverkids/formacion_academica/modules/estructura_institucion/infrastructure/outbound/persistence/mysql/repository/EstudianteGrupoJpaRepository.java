package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstudianteGrupoJpaRepository extends JpaRepository<EstudianteGrupoEntity, Long> {

    List<EstudianteGrupoEntity> findByGrupoId(Long grupoId);
}
