package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CrearClasesMasivasDto {

    @NotEmpty(message = "Debe proporcionar al menos una clase para creación masiva")
    private List<CrearClaseDto> clases;

    public CrearClasesMasivasDto(List<CrearClaseDto> clases) {
        this.clases = Objects.requireNonNull(clases, "Debe proporcionar al menos una clase para creación masiva");
        if (this.clases.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una clase para creación masiva");
        }
    }
}
