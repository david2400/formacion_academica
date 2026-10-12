package com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearProfesorDto {
    @NotNull(message = "Selecciona el usuario")
    private Long usuarioId;

    @NotNull(message = "La empresa es obligatoria")
    private Long empresaId;

    private LocalDate fechaVinculacion;

    @Size(max = 500, message = "Las observaciones no pueden superar 500 caracteres")
    private String observaciones;
}
