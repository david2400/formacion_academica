package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudValidacionRespuesta {

    // Para opción múltiple única
    private Long idOpcionSeleccionada;
    // Para opción múltiple múltiple
    private List<Long> idsOpcionesSeleccionadas;
    // Para verdadero/falso
    private Boolean respuestaBooleana;
    // Para respuesta abierta corta/larga
    private String respuestaTexto;
    // Para numérico
    private BigDecimal respuestaNumerica;
    // Para escala
    private Integer valorEscala;
    // Para ordenamiento
    private List<Long> idsItemsOrdenados;
    // Para emparejamiento
    private Map<Long, Long> paresEmparejados;

}
