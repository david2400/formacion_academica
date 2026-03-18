package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel;

import java.time.LocalDateTime;

public record NivelEducativoDto(
    Long id,
    String codigo,
    String nombre,
    String descripcion,
    Integer orden,
    Long nivelSuperiorId,
    boolean activo,
    String categoria,
    LocalDateTime creadoEn,
    LocalDateTime actualizadoEn
) {
    public NivelEducativoDto(Long id, String codigo, String nombre, String descripcion, Integer orden, Long nivelSuperiorId, String categoria) {
        this(id, codigo, nombre, descripcion, orden, nivelSuperiorId, true, categoria, null, null);
    }
}
