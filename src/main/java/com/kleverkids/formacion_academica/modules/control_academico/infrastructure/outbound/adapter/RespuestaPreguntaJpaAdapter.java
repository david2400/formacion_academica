package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.respuesta_pregunta.RespuestaPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.ActualizarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaPersistenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.RespuestaPreguntaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.RespuestaPreguntaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.EstudianteExamenJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.RespuestaPreguntaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class RespuestaPreguntaJpaAdapter implements RespuestaPreguntaRepositoryPort {

    private final RespuestaPreguntaJpaRepository respuestaPreguntaJpaRepository;
    private final EstudianteExamenJpaRepository estudianteExamenJpaRepository;
    private final RespuestaPreguntaMapper respuestaPreguntaMapper;

    @Override
    @Transactional
    public RespuestaPreguntaDto registrar(RegistrarRespuestaPreguntaPersistenceDto request) {
        EstudianteExamenEntity relacion = estudianteExamenJpaRepository.findById(request.estudianteExamenId())
                .orElseThrow(() -> new IllegalArgumentException("Relación estudiante-examen no encontrada"));
        RespuestaPreguntaEntity entity = respuestaPreguntaMapper.toEntity(request, relacion);
        return respuestaPreguntaMapper.toDto(respuestaPreguntaJpaRepository.save(entity));
    }

    @Override
    @Transactional
    public RespuestaPreguntaDto actualizar(ActualizarRespuestaPreguntaDto request) {
        RespuestaPreguntaEntity entity = respuestaPreguntaJpaRepository.findById(request.id())
                .orElseThrow(() -> new IllegalArgumentException("Respuesta de pregunta no encontrada"));
        respuestaPreguntaMapper.applyUpdate(entity, request);
        return respuestaPreguntaMapper.toDto(respuestaPreguntaJpaRepository.save(entity));
    }

    @Override
    public List<RespuestaPreguntaDto> listarPorEstudiante(UUID examenId, UUID estudianteId) {
        return respuestaPreguntaMapper.toDtoList(
                respuestaPreguntaJpaRepository.findByExamenIdAndEstudianteIdOrderByRegistradaEnAsc(examenId, estudianteId)
        );
    }
}
