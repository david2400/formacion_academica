package com.kleverkids.formacion_academica.modules.control_academico;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.AsistenciaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CalificacionPersonalizadaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ClaseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CriterioExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.IntentoExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.ObservacionCriterioEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.PreguntaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaBancoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaCriterioEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaPreguntaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaIntentoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.TematicaExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.AsistenciaJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.CalificacionPersonalizadaJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ClaseJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.CriterioExamenJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.EstudianteExamenJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamenJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.IntentoExamenJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ObservacionCriterioJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.PreguntaBancoJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.RespuestaBancoJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.RespuestaCriterioJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.RespuestaPreguntaJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.RespuestaIntentoJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.TematicaExamenJpaRepository;
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
        TematicaExamenEntity.class
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
        RespuestaBancoJpaRepository.class,
        RespuestaCriterioJpaRepository.class,
        RespuestaPreguntaJpaRepository.class,
        RespuestaIntentoJpaRepository.class,
        TematicaExamenJpaRepository.class
})
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.control_academico")
public class ControlAcademicoModuleConfig {
}
