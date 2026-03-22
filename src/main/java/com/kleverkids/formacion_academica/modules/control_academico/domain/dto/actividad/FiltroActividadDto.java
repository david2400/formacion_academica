package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroActividadDto {

    private Long cursoId;
    private Long moduloId;
    private String tipo;
    private String estado;
    private String titulo; // búsqueda por texto
    private Boolean obligatoria;
    private LocalDateTime fechaInicioDesde;
    private LocalDateTime fechaInicioHasta;
    private LocalDateTime fechaFinDesde;
    private LocalDateTime fechaFinHasta;
    private List<String> etiquetas;
    private Boolean requiereEvaluacion;
    private Integer pagina;
    private Integer tamañoPagina;
    private String ordenarPor; // "titulo", "fechaInicio", "orden"
    private String direccion; // "ASC", "DESC"
}
