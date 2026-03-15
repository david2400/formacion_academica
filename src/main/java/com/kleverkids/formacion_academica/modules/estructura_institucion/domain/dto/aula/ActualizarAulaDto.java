package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula;

import com.kleverkids.formacion_academica.shared.common.domain.dto.AuditInfoDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ActualizarAulaDto extends CrearAulaDto {

    @NotNull(message = "El identificador del aula es obligatorio")
    private Long id;

    public Long id() {
        return id;
    }

}
