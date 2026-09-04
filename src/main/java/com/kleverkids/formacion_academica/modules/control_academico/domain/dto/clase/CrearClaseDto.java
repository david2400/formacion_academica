package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CrearClaseDto {

    @NotBlank(message = "El nombre de la clase es obligatorio")
    private String nombre;

    /** Inicio de la clase, con hora (ej. 2026-09-08T17:00:00). */
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    /** Fin de la clase. Si no se envía, se asume igual al inicio. */
    private LocalDateTime fechaFin;

    private List<Long> profesoresIds;

    /** Tipo de clase del catálogo (tipos_clase). Opcional. */
    private Long tipoClaseId;

    /** Si no se envía, la clase se crea como PROGRAMADA. */
    private EstadoClase estado;

    /** Anotación inicial opcional; se registra como primera entrada de la bitácora. */
    private String observacion;

}
