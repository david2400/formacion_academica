package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroActivityDto {

    /** Coincidencia parcial, sin distinguir mayúsculas, sobre el título. */
    private String texto;

    private Long activityTypeId;
    private EstadoActivity estado;

    public boolean estaVacio() {
        return (texto == null || texto.isBlank())
                && activityTypeId == null
                && estado == null;
    }
}
