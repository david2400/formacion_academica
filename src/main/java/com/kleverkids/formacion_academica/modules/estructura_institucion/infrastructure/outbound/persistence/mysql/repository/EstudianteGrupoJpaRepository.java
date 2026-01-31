package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EstudianteGrupoJpaRepository extends JpaRepository<EstudianteGrupoEntity, UUID> {

    List<EstudianteGrupoEntity> findByGrupoId(UUID grupoId);
}
