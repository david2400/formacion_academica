package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearGrupoDto {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El grado es obligatorio")
    private Long gradoId;

    @Positive(message = "La capacidad debe ser positiva")
    private Integer capacidadMaxima;

    private Long tutorId;
    private Long aulaId;

    public String codigo() {
        return codigo;
    }

    public String nombre() {
        return nombre;
    }

    public Long gradoId() {
        return gradoId;
    }

    public Integer capacidadMaxima() {
        return capacidadMaxima;
    }

    public Long tutorId() {
        return tutorId;
    }

    public Long aulaId() {
        return aulaId;
    }
}
