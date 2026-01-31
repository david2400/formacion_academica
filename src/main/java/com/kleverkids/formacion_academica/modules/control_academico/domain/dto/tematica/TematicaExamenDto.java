package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica;

import java.util.UUID;

public record TematicaExamenDto(UUID id,
                                UUID examenId,
                                String titulo,
                                String descripcion,
                                Integer orden) {
}
