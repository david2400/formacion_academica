package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarMatriculaDto extends CrearMatriculaDto {

    @NotNull(message = "La matrícula es obligatoria")
    private Long matriculaId;

}
