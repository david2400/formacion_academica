package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Criterios de búsqueda para listar clases. Todos son opcionales: los nulos no
 * aplican ninguna restricción.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroClasesDto {

    /** Coincidencia parcial, sin distinguir mayúsculas, sobre nombre o código. */
    private String texto;

    private EstadoClase estado;

    /** Filtra por el día de fecha_inicio, inclusive. */
    private LocalDate desde;

    /** Filtra por el día de fecha_inicio, inclusive. */
    private LocalDate hasta;

    public boolean estaVacio() {
        return (texto == null || texto.isBlank())
                && estado == null
                && desde == null
                && hasta == null;
    }
}
