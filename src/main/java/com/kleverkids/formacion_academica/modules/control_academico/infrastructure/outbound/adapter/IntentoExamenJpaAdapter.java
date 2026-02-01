package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.intento.IntentoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.IntentoExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.IntentoExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.RespuestaIntentoEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.IntentoExamenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class IntentoExamenJpaAdapter implements IntentoExamenRepositoryPort {

    private final IntentoExamenJpaRepository intentoExamenJpaRepository;
    private final IntentoExamenMapper intentoExamenMapper;

    @Override
    @Transactional
    public IntentoExamenDto iniciar(IniciarIntentoExamenDto request) {
        IntentoExamenEntity existente = intentoExamenJpaRepository
                .findFirstByExamenIdAndEstudianteIdAndEstado(request.examenId(), request.estudianteId(), "EN_PROGRESO")
                .orElse(null);
        if (existente != null) {
            return intentoExamenMapper.toDto(existente);
        }
        IntentoExamenEntity entity = intentoExamenMapper.toEntity(request);
        return intentoExamenMapper.toDto(intentoExamenJpaRepository.save(entity));
    }

    @Override
    @Transactional
    public RespuestaIntentoDto registrarRespuesta(RegistrarRespuestaIntentoDto request) {
        IntentoExamenEntity intento = intentoExamenJpaRepository.findById(request.intentoId())
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
        if (!"EN_PROGRESO".equals(intento.getEstado())) {
            throw new IllegalStateException("Solo se pueden registrar respuestas en intentos en progreso");
        }
        RespuestaIntentoEntity respuesta = intentoExamenMapper.toEntity(request, intento);
        intento.getRespuestas().add(respuesta);
        intentoExamenJpaRepository.save(intento);
        return intentoExamenMapper.toRespuestaDto(respuesta);
    }

    @Override
    @Transactional
    public IntentoExamenDto finalizar(FinalizarIntentoExamenDto request) {
        IntentoExamenEntity intento = intentoExamenJpaRepository.findById(request.intentoId())
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
        intentoExamenMapper.applyFinalizacion(intento, request);
        return intentoExamenMapper.toDto(intentoExamenJpaRepository.save(intento));
    }

    @Override
    public List<IntentoExamenDto> listarPorEstudiante(UUID examenId, UUID estudianteId) {
        return intentoExamenMapper.toDtoList(
                intentoExamenJpaRepository.findByExamenIdAndEstudianteIdOrderByIniciadoEnDesc(examenId, estudianteId)
        );
    }
}
