package com.kleverkids.formacion_academica.modules.estados.application.input.transicion;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.persistence.mysql.entity.EstadoTransicionEntity;

import java.util.List;

public interface ConsultarTransicionUseCase {
    List<EstadoTransicionEntity> listarPorModulo(Long idModulo);
    List<EstadoTransicionEntity> listarDesdeEstado(Long estadoOrigenId, Long idModulo);
}
