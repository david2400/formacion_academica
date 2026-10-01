package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.PreguntaLookupPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Pregunta;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.PreguntaDomainMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.PreguntaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PreguntaLookupJpaAdapter implements PreguntaLookupPort {

    private final PreguntaJpaRepository preguntaJpaRepository;
    private final PreguntaDomainMapper preguntaDomainMapper;

    @Override
    public Optional<Pregunta> obtenerPorId(Long id) {
        return preguntaJpaRepository.findById(id).map(preguntaDomainMapper::toDomain);
    }
}
