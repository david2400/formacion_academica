package com.kleverkids.formacion_academica.modules.estructura_institucion;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {
        "com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.entity"
})
@EnableJpaRepositories(basePackages = {
        "com.kleverkids.formacion_academica.modules.estructura_institucion.infrastructure.outbound.persistence.mysql.repository"
})
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.estructura_institucion")
public class EstructuraInstitucionModuleConfig {
}
