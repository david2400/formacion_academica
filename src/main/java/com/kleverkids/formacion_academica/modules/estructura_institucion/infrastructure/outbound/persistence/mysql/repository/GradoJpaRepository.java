package com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradoJpaRepository extends JpaRepository<GradoEntity, Long> {

    boolean existsByNombreAndNivelEducativo(String nombre, String nivelEducativo);
}
