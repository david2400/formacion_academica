package com.kleverkids.formacion_academica.modules.usuarios.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Profesor del instituto = usuario de access_control vinculado a una empresa con rol docente.
 *
 * <p>{@code usuarioId} es el identificador que usan las clases ({@code clase_profesores.profesor_id})
 * y el token de access_control ({@code sub}); por eso un mismo usuario es profesor una sola vez por empresa.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profesor {
    private Long id;
    private Long usuarioId;
    private Long empresaId;
    private boolean activo;
    private LocalDate fechaVinculacion;
    private String observaciones;
}
