package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.intento.IntentoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.IntentoExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.IntentoExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.RespuestaIntentoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.IntentoExamenJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class IntentoExamenJpaAdapter implements IntentoExamenRepositoryPort {

    private final IntentoExamenJpaRepository intentoExamenJpaRepository;

    public IntentoExamenJpaAdapter(IntentoExamenJpaRepository intentoExamenJpaRepository) {
        this.intentoExamenJpaRepository = intentoExamenJpaRepository;
    }

    @Override
    @Transactional
    public IntentoExamenDto iniciar(IniciarIntentoExamenDto request) {
        IntentoExamenEntity existente = intentoExamenJpaRepository
                .findFirstByExamenIdAndEstudianteIdAndEstado(request.examenId(), request.estudianteId(), "EN_PROGRESO")
                .orElse(null);
        if (existente != null) {
            return IntentoExamenMapper.toDto(existente);
        }
        IntentoExamenEntity entity = IntentoExamenMapper.toEntity(request);
        return IntentoExamenMapper.toDto(intentoExamenJpaRepository.save(entity));
    }

    @Override
    @Transactional
    public RespuestaIntentoDto registrarRespuesta(RegistrarRespuestaIntentoDto request) {
        IntentoExamenEntity intento = intentoExamenJpaRepository.findById(request.intentoId())
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
        if (!"EN_PROGRESO".equals(intento.getEstado())) {
            throw new IllegalStateException("Solo se pueden registrar respuestas en intentos en progreso");
        }
        RespuestaIntentoEntity respuesta = IntentoExamenMapper.toEntity(request, intento);
        intento.getRespuestas().add(respuesta);
        intentoExamenJpaRepository.save(intento);
        return IntentoExamenMapper.toDto(respuesta);
    }

    @Override
    @Transactional
    public IntentoExamenDto finalizar(FinalizarIntentoExamenDto request) {
        IntentoExamenEntity intento = intentoExamenJpaRepository.findById(request.intentoId())
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
        IntentoExamenMapper.applyFinalizacion(intento, request);
        return IntentoExamenMapper.toDto(intentoExamenJpaRepository.save(intento));
    }

    @Override
    public List<IntentoExamenDto> listarPorEstudiante(UUID examenId, UUID estudianteId) {
        return intentoExamenJpaRepository.findByExamenIdAndEstudianteIdOrderByIniciadoEnDesc(examenId, estudianteId)
                .stream()
                .map(IntentoExamenMapper::toDto)
                .toList();
    }
}
