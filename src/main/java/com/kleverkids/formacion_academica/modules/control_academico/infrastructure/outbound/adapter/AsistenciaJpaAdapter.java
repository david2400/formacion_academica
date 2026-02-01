package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.asistencia.AsistenciaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.HistorialAsistenciaFiltroDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.RegistrarAsistenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.AsistenciaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.AsistenciaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.AsistenciaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AsistenciaJpaAdapter implements AsistenciaRepositoryPort {

    private final AsistenciaJpaRepository asistenciaJpaRepository;
    private final AsistenciaMapper asistenciaMapper;

    @Override
    public AsistenciaDto guardar(RegistrarAsistenciaDto request) {
        AsistenciaEntity entity = asistenciaMapper.toEntity(request);
        return asistenciaMapper.toDto(asistenciaJpaRepository.save(entity));
    }

    @Override
    public HistorialAsistenciaDto consultarHistorial(HistorialAsistenciaFiltroDto filtro) {
        List<AsistenciaEntity> entities = filtrarEntidades(filtro);
        return new HistorialAsistenciaDto(
                filtro.estudianteId(),
                filtro.claseId(),
                filtro.desde(),
                filtro.hasta(),
                asistenciaMapper.toDtoList(entities)
        );
    }

    private List<AsistenciaEntity> filtrarEntidades(HistorialAsistenciaFiltroDto filtro) {
        UUID estudianteId = filtro.estudianteId();
        UUID claseId = filtro.claseId();
        LocalDate desde = filtro.desde();
        LocalDate hasta = filtro.hasta();

        if (claseId != null && desde != null && hasta != null) {
            return asistenciaJpaRepository.findByClaseIdAndFechaRegistroBetween(
                    claseId,
                    desde.atStartOfDay(),
                    LocalDateTime.of(hasta, LocalTime.MAX)
            );
        }

        if (estudianteId != null && claseId != null) {
            return asistenciaJpaRepository.findByEstudianteIdAndClaseId(estudianteId, claseId);
        }

        if (estudianteId != null) {
            return asistenciaJpaRepository.findByEstudianteId(estudianteId);
        }

        if (claseId != null) {
            return asistenciaJpaRepository.findByClaseId(claseId);
        }

        return asistenciaJpaRepository.findAll();
    }
}
