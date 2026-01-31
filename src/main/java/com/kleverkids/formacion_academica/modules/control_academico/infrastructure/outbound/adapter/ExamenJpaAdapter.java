package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CalificacionPersonalizadaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.CalificacionPersonalizadaJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamenJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ExamenJpaAdapter implements ExamenRepositoryPort {

    private final ExamenJpaRepository examenJpaRepository;
    private final CalificacionPersonalizadaJpaRepository calificacionRepository;

    public ExamenJpaAdapter(ExamenJpaRepository examenJpaRepository,
                            CalificacionPersonalizadaJpaRepository calificacionRepository) {
        this.examenJpaRepository = examenJpaRepository;
        this.calificacionRepository = calificacionRepository;
    }

    @Override
    public ExamenDto guardar(CrearExamenDto request) {
        return ExamenMapper.toDto(examenJpaRepository.save(ExamenMapper.toEntity(request)));
    }

    @Override
    public CalificacionPersonalizadaDto registrarCalificacion(RegistrarCalificacionPersonalizadaDto request) {
        CalificacionPersonalizadaEntity entity = ExamenMapper.toEntity(request);
        return ExamenMapper.toDto(calificacionRepository.save(entity));
    }
}
