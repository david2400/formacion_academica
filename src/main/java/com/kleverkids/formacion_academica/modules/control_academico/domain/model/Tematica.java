package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.time.LocalDateTime;

public record Tematica(Long id,
                             String titulo,
                             String descripcion,
                             boolean eliminado,
                             Integer usrCrea,
                             Integer usrMod,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {
}
