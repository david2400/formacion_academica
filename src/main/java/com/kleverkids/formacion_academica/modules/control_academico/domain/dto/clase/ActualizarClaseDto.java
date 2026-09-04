package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
public class ActualizarClaseDto {

    @NotNull(message = "El id de la clase es obligatorio")
    private Long id;

    @NotBlank(message = "El nombre de la clase es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private List<Long> profesoresIds;

    /** Tipo de clase del catálogo (tipos_clase). Opcional. */
    private Long tipoClaseId;

    private EstadoClase estado;

    /**
     * Anotación opcional que se AGREGA a la bitácora. Las observaciones previas
     * no se modifican; para eso está el endpoint de observaciones.
     */
    private String observacion;
}
