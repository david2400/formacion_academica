package com.kleverkids.formacion_academica.modules.estructura_institucion;

import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.EstudianteGrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GradoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.GrupoEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SalonEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity.SedeEntity;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.EstudianteGrupoJpaRepository;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GradoJpaRepository;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.GrupoJpaRepository;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.SalonJpaRepository;
import com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository.SedeJpaRepository;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
        // AulaEntity.class,
        EstudianteGrupoEntity.class,
        GradoEntity.class,
        GrupoEntity.class,
        SalonEntity.class,
        SedeEntity.class
})
@EnableJpaRepositories(basePackageClasses = {
        // AulaJpaRepository.class,
        EstudianteGrupoJpaRepository.class,
        GradoJpaRepository.class,
        GrupoJpaRepository.class,
        SalonJpaRepository.class,
        SedeJpaRepository.class
})
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.estructura_institucion")
public class EstructuraInstitucionModuleConfig {
}
