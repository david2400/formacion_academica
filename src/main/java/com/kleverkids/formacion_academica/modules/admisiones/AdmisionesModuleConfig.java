package com.kleverkids.formacion_academica.modules.admisiones;

import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.InscripcionEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity.MatriculaEntity;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.InscripcionJpaRepository;
import com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.repository.MatriculaJpaRepository;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
        InscripcionEntity.class,
        MatriculaEntity.class
})
@EnableJpaRepositories(basePackageClasses = {
        InscripcionJpaRepository.class,
        MatriculaJpaRepository.class
})
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.admisiones")
public class AdmisionesModuleConfig {
}
