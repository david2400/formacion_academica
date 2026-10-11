package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.inbound.rest.feedback;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/** Paginación acotada para los endpoints de Feedback (máximo 100 por página). */
final class Paginacion {

    static final int MAXIMO = 100;

    private Paginacion() {
    }

    static Pageable de(int page, int size, Sort sort) {
        return PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), MAXIMO), sort);
    }

    static Pageable recientes(int page, int size) {
        return de(page, size, Sort.by(Sort.Direction.DESC, "id"));
    }
}
