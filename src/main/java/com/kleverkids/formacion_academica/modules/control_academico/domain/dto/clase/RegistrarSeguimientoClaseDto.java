package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Actualización parcial del seguimiento de una clase, pensada para marcarla como
 * dictada o cancelada y dejar observaciones sin tener que reenviar la clase
 * completa. Los campos nulos no se modifican.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarSeguimientoClaseDto {

    private EstadoClase estado;

    private String observaciones;
}
