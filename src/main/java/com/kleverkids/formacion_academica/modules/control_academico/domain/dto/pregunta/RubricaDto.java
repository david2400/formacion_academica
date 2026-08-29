package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RubricaDto {
    private List<CriterioDto> criterios;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CriterioDto {
            private String nombre;
            private String descripcion;
            private Integer puntajeMaximo;
            private List<NivelDto> niveles;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NivelDto {
            private String nombre;
            private String descripcion;
            private Integer puntaje;
    }

}
