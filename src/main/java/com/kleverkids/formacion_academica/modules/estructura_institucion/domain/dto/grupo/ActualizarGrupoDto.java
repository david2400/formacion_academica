package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.shared.common.domain.dto.AuditInfoDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarGrupoDto extends CrearGrupoDto {

    @NotNull(message = "El identificador del grupo es obligatorio")
    private UUID id;
}
