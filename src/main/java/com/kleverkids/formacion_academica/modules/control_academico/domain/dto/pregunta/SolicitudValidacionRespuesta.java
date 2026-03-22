package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public record SolicitudValidacionRespuesta(
    // Para opción múltiple única
    Long idOpcionSeleccionada,
    // Para opción múltiple múltiple
    List<Long> idsOpcionesSeleccionadas,
    // Para verdadero/falso
    Boolean respuestaBooleana,
    // Para respuesta abierta corta/larga
    String respuestaTexto,
    // Para numérico
    BigDecimal respuestaNumerica,
    // Para escala
    Integer valorEscala,
    // Para ordenamiento
    List<Long> idsItemsOrdenados,
    // Para emparejamiento
    Map<Long, Long> paresEmparejados
) {}
