package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

public record NivelEducativo(Long id,
                              String codigo,
                              String nombre,
                              String descripcion,
                              Integer orden,
                              Long nivelSuperiorId,
                            //   String categoria,
                              boolean activo,
                              Integer usrCrea,
                              Integer usrMod,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}
