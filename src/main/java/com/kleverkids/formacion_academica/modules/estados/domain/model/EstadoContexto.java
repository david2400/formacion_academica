package com.kleverkids.formacion_academica.modules.estados.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Estado del catálogo tal como aplica a un contexto.
 *
 * <p>El catálogo y su parametrización viven en el servicio <b>access_control</b>
 * (endpoint {@code /api/access_control/estados/contextos/{codigo}/estados}). Esta
 * clase es la proyección local de esa respuesta; aquí no se persiste nada.
 *
 * <p><b>Contrato:</b> {@code codigo} es el identificador estable. {@code estadoId}
 * lo genera la base de datos de access_control y puede variar entre entornos:
 * sirve para guardarlo y devolverlo, nunca para escribirlo en código.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstadoContexto {

    /** Id de la parametrización en access_control. */
    private Long id;

    /** Terna aplicación.modulo.entidad. */
    private String contexto;

    private Long estadoId;

    private String codigo;
    private String nombre;
    private String descripcion;
    private String color;
    private String icono;

    private Boolean esInicial;
    private Boolean esFinal;
    private Integer orden;

    private Long companyId;
}
