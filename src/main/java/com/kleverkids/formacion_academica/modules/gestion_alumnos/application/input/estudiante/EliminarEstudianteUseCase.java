package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.estudiante;

import java.util.UUID;

public interface EliminarEstudianteUseCase {

    void eliminar(UUID estudianteId);
}
