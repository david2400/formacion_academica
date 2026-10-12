package com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/** El usuario y la empresa no se cambian: para eso se elimina y se crea otro profesor. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarProfesorDto {
    private LocalDate fechaVinculacion;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;
}
