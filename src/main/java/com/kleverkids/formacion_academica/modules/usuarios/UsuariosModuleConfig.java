package com.kleverkids.formacion_academica.modules.usuarios;

import com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.entity.ProfesorEntity;
import com.kleverkids.formacion_academica.modules.usuarios.infrastructure.outbound.persistence.mysql.repository.ProfesorJpaRepository;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Módulo de usuarios del instituto (por ahora: profesores).
 *
 * <p>Un profesor NO duplica datos de la persona: es la vinculación de un usuario
 * de access_control ({@code security.User}) con una empresa, más los datos propios
 * de su rol docente. Nombre, documento y usuario se leen siempre de access_control.
 */
@Configuration
@EntityScan(basePackageClasses = ProfesorEntity.class)
@EnableJpaRepositories(basePackageClasses = ProfesorJpaRepository.class)
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.usuarios")
public class UsuariosModuleConfig {
}
