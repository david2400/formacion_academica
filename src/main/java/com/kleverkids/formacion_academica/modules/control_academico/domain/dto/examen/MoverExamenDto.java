package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MoverExamenDto {

    @NotNull(message = "El examen es obligatorio")
    private UUID examenId;

    @NotNull(message = "La clase destino es obligatoria")
    private UUID claseDestinoId;

    public MoverExamenDto(UUID examenId, UUID claseDestinoId) {
        this.examenId = Objects.requireNonNull(examenId, "El examen es obligatorio");
        this.claseDestinoId = Objects.requireNonNull(claseDestinoId, "La clase destino es obligatoria");
    }
}
