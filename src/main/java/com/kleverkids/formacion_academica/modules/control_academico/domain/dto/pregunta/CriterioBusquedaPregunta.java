package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;

public record CriterioBusquedaPregunta(
    String tipoPregunta,
    String dificultad,
    Long temaId,
    List<String> etiquetas,
    String textoBusqueda,
    Boolean incluirEliminados
) {
    public CriterioBusquedaPregunta {
        if (incluirEliminados == null) {
            incluirEliminados = false;
        }
    }
    
    public static CriterioBusquedaPregunta empty() {
        return new CriterioBusquedaPregunta(null, null, null, null, null, false);
    }
}
