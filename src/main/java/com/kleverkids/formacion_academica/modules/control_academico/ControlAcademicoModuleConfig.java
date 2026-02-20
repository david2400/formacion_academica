package com.kleverkids.formacion_academica.modules.control_academico;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.*;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.ExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.IntentoExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.*;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
        AsistenciaEntity.class,
        CalificacionPersonalizadaEntity.class,
        ClaseEntity.class,
        CriterioExamenEntity.class,
        EstudianteExamenEntity.class,
        ExamenEntity.class,
        IntentoExamenEntity.class,
        ObservacionCriterioEntity.class,
        PreguntaBancoEntity.class,
        RespuestaBancoEntity.class,
        RespuestaCriterioEntity.class,
        RespuestaPreguntaEntity.class,
        RespuestaIntentoEntity.class,
        TematicaEntity.class
})
@EnableJpaRepositories(basePackageClasses = {
        AsistenciaJpaRepository.class,
        CalificacionPersonalizadaJpaRepository.class,
        ClaseJpaRepository.class,
        CriterioExamenJpaRepository.class,
        EstudianteExamenJpaRepository.class,
        ExamenJpaRepository.class,
        IntentoExamenJpaRepository.class,
        ObservacionCriterioJpaRepository.class,
        PreguntaBancoJpaRepository.class,
        RespuestaCriterioJpaRepository.class,
        RespuestaPreguntaJpaRepository.class,
        TematicaJpaRepository.class
})
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.control_academico")
public class ControlAcademicoModuleConfig {
}
