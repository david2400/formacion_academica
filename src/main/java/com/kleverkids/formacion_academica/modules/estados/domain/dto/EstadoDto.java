package com.kleverkids.formacion_academica.modules.estados.domain.dto;

import java.time.LocalDateTime;

public record EstadoDto(
    Long id,
    String uuid,
    String codigo,
    String nombre,
    String descripcion,
    String color,
    String icono,
    Long idModulo,
    Long idEmpresa,
    Boolean esInicial,
    Boolean esFinal,
    Integer orden,
    Boolean activo,
    String metadata,
    LocalDateTime creadoEn,
    LocalDateTime actualizadoEn
) {
    public EstadoDto(Long id, String uuid, String codigo, String nombre, String descripcion, 
                    String color, String icono, Long idModulo, Boolean esInicial, Boolean esFinal, Integer orden) {
        this(id, uuid, codigo, nombre, descripcion, color, icono, idModulo, null, esInicial, esFinal, orden, true, null, null, null);
    }
}
