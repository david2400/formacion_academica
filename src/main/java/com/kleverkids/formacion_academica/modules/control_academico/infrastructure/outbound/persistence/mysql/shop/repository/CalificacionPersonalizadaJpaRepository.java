package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CalificacionPersonalizadaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CalificacionPersonalizadaJpaRepository extends JpaRepository<CalificacionPersonalizadaEntity, UUID> {
}
