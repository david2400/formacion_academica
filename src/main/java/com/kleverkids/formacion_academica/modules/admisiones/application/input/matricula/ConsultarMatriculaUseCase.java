package com.kleverkids.formacion_academica.modules.admisiones.application.input.matricula;

import com.kleverkids.formacion_academica.modules.admisiones.domain.model.Matricula;
import java.util.Optional;

public interface ConsultarMatriculaUseCase {

    Optional<Matricula> consultarPorId(Long matriculaId);
}
