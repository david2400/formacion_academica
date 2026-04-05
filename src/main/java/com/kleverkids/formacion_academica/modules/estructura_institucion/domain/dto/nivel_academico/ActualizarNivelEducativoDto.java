package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarNivelEducativoDto extends CrearNivelEducativoDto {
    
    @NotNull(message = "El identificador es obligatorio")
    private Long id;

}
