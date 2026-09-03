package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase.EstadoClase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CrearClaseDto {

    @NotBlank(message = "El nombre de la clase es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    /** Si no se envía, se asume una clase de un solo día (igual a fechaInicio). */
    private LocalDate fechaFin;

    private List<Long> profesoresIds;

    /** Si no se envía, la clase se crea como PROGRAMADA. */
    private EstadoClase estado;

    private String observaciones;

}
