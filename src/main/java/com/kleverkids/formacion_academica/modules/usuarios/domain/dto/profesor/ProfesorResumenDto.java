package com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Nombre para mostrar de un profesor a partir de su usuarioId (clases, feedback, resultados). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorResumenDto {
    private Long usuarioId;
    private Long profesorId;
    private String nombreCompleto;
    private boolean activo;
}
