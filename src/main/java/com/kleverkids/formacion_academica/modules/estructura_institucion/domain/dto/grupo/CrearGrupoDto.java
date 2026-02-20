package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

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
    private UUID gradoId;

    @Positive(message = "La capacidad debe ser positiva")
    private Integer capacidadMaxima;

    private UUID tutorId;
    private UUID aulaId;

    public String codigo() {
        return codigo;
    }

    public String nombre() {
        return nombre;
    }

    public UUID gradoId() {
        return gradoId;
    }

    public Integer capacidadMaxima() {
        return capacidadMaxima;
    }

    public UUID tutorId() {
        return tutorId;
    }

    public UUID aulaId() {
        return aulaId;
    }
}
