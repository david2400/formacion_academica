package com.kleverkids.formacion_academica.modules.control_academico.domain.model.tematica;

import java.util.UUID;

public record TematicaExamen(UUID id,
                             UUID examenId,
                             String titulo,
                             String descripcion,
                             Integer orden) {
}
