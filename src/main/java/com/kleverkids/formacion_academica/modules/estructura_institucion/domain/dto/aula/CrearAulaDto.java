package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearAulaDto {

    @NotBlank(message = "El nombre del aula es obligatorio")
    private String nombre;
    private String descripcion;
    private Integer capacidad;
    private Boolean activo;

    public String nombre() {
        return nombre;
    }

    public String descripcion() {
        return descripcion;
    }

    public Integer capacidad() {
        return capacidad;
    }

    public Boolean activo() {
        return activo;
    }
}
