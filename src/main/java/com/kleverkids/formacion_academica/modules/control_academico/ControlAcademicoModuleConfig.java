package com.kleverkids.formacion_academica.modules.control_academico;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.*;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.*;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.*;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackageClasses = {
        ActividadEntity.class,
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
        TematicaEntity.class,
        // Entidades de preguntas en español
        PreguntaOpcionMultipleEntity.class,
        PreguntaOpcionMultipleUnicaEntity.class,
        PreguntaNumericaEntity.class,
        PreguntaAbiertaCortaEntity.class,
        PreguntaAbiertaLargaEntity.class,
        PreguntaVerdaderoFalsoEntity.class,
        PreguntaOrdenamientoEntity.class,
        PreguntaEmparejamientoEntity.class,
        PreguntaEscalaEntity.class,
        // Nuevas entidades unificadas en español
        PreguntaEntity.class,
        PreguntaExamenEmbeddable.class,
        ResultadoExamenEntity.class,
        EnvioExamenEntity.class,
        RespuestaPreguntaEmbeddable.class,
        ResultadoPreguntaEmbeddable.class
})
@EnableJpaRepositories(basePackageClasses = {
        ActividadJpaRepository.class,
        AsistenciaJpaRepository.class,
        CalificacionPersonalizadaJpaRepository.class,
        ClaseJpaRepository.class,
        CriterioExamenJpaRepository.class,
        EstudianteExamenJpaRepository.class,
        ExamenJpaRepository.class,
        IntentoExamenJpaRepository.class,
        ObservacionCriterioJpaRepository.class,
        PreguntaBancoJpaRepository.class,
        PreguntaJpaRepository.class,
        RespuestaCriterioJpaRepository.class,
        RespuestaPreguntaJpaRepository.class,
        TematicaJpaRepository.class
})
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.control_academico")
public class ControlAcademicoModuleConfig {
}
