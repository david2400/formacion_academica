package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula;

import com.kleverkids.formacion_academica.shared.common.domain.dto.AuditInfoDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarAulaDto extends CrearAulaDto {

    @NotNull(message = "El identificador del aula es obligatorio")
    private UUID id;

    public UUID id() {
        return id;
    }

}
