package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.estudiantegrupo;

import java.time.LocalDate;
import java.util.UUID;

public record EstudianteGrupo(UUID id,
                              UUID estudianteId,
                              UUID grupoId,
                              LocalDate fechaAsignacion,
                              String estado) {
}
