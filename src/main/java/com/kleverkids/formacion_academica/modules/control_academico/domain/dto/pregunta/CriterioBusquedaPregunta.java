package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
public class CriterioBusquedaPregunta {
    private String tipoPregunta;
    private String dificultad;
    private Long temaId;
    private List<String> etiquetas;
    private String textoBusqueda;
    private Boolean incluirEliminados;

public CriterioBusquedaPregunta(String tipoPregunta, String dificultad, Long temaId, List<String> etiquetas, String textoBusqueda, Boolean incluirEliminados) {
        this.tipoPregunta = tipoPregunta;
        this.dificultad = dificultad;
        this.temaId = temaId;
        this.etiquetas = etiquetas;
        this.textoBusqueda = textoBusqueda;
        this.incluirEliminados = incluirEliminados != null ? incluirEliminados : false;
    }
    
    public static CriterioBusquedaPregunta empty() {
        return new CriterioBusquedaPregunta(null, null, null, null, null, false);
    }

}
