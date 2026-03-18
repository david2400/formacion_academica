package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

public record Grado(Long id,
                    String nombre,
                    Long nivelEducativoId,
                    Integer orden) {
}
