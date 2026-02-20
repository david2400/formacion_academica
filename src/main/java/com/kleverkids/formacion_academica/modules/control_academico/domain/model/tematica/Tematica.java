package com.kleverkids.formacion_academica.modules.control_academico.domain.model.tematica;

import java.util.UUID;

public record Tematica(UUID id,
                             String titulo,
                             String descripcion,
                             Integer orden) {
}
