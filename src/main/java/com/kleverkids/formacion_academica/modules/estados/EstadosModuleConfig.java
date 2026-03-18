package com.kleverkids.formacion_academica.modules.estados;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EntidadEstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoHistorialEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoTransicionEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EntidadEstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoHistorialJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoJpaRepository;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoTransicionJpaRepository;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuración del módulo de Estados
 * Sigue el patrón de configuración de los demás módulos del proyecto
 */
@Configuration
@EnableJpaRepositories(basePackageClasses = {
        EstadoJpaRepository.class,
        EntidadEstadoJpaRepository.class,
        EstadoHistorialJpaRepository.class,
        EstadoTransicionJpaRepository.class
})
@EntityScan(basePackageClasses = {
        EstadoEntity.class,
        EntidadEstadoEntity.class,
        EstadoHistorialEntity.class,
        EstadoTransicionEntity.class
})
public class EstadosModuleConfig {
    
    // Aquí se pueden agregar configuraciones específicas del módulo de estados
    // Beans, configuraciones, etc.
    
}
