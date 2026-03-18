package com.kleverkids.formacion_academica.modules.estados.application.services;

import com.kleverkids.formacion_academica.modules.estados.application.input.transicion.ConsultarTransicionUseCase;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoTransicionEntity;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.repository.EstadoTransicionJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TransicionService implements ConsultarTransicionUseCase {

    private final EstadoTransicionJpaRepository transicionRepository;

    @Override
    public List<EstadoTransicionEntity> listarPorModulo(Long idModulo) {
        return transicionRepository.findByIdModuloAndActivaTrue(idModulo);
    }

    @Override
    public List<EstadoTransicionEntity> listarDesdeEstado(Long estadoOrigenId, Long idModulo) {
        return transicionRepository.findTransicionesDesdeEstado(estadoOrigenId, idModulo);
    }
}
