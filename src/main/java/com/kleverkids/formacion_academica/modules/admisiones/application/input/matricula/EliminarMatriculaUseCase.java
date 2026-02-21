package com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula;

import java.util.UUID;

public interface EliminarMatriculaUseCase {

    void eliminar(UUID matriculaId);
}
