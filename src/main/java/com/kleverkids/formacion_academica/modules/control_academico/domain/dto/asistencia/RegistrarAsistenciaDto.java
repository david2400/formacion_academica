package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RegistrarAsistenciaDto {

    @NotNull(message = "La clase es obligatoria")
    private UUID claseId;

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    private LocalDateTime fechaRegistro;

    private boolean presente;

    public RegistrarAsistenciaDto(UUID claseId,
                                  UUID estudianteId,
                                  LocalDateTime fechaRegistro,
                                  boolean presente) {
        this.claseId = Objects.requireNonNull(claseId, "La clase es obligatoria");
        this.estudianteId = Objects.requireNonNull(estudianteId, "El estudiante es obligatorio");
        this.fechaRegistro = fechaRegistro;
        this.presente = presente;
    }

    public UUID getClaseId() {
        return claseId;
    }

    public void setClaseId(UUID claseId) {
        this.claseId = claseId;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(UUID estudianteId) {
        this.estudianteId = estudianteId;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}
