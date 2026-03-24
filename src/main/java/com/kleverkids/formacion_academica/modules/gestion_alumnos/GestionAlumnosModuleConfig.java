package com.kleverkids.formacion_academica.modules.gestion_alumnos;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.AcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.EstudianteAcudienteEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity.ParentescoEntity;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.AcudienteJpaRepository;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteJpaRepository;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.EstudianteAcudienteJpaRepository;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.repository.ParentescoJpaRepository;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
        EstudianteEntity.class,
        AcudienteEntity.class,
        EstudianteAcudienteEntity.class,
        ParentescoEntity.class
})
@EnableJpaRepositories(basePackageClasses = {
        EstudianteJpaRepository.class,
        AcudienteJpaRepository.class,
        EstudianteAcudienteJpaRepository.class,
        ParentescoJpaRepository.class
})
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.gestion_alumnos")
public class GestionAlumnosModuleConfig {
}
