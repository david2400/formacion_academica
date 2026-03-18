package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente;
import java.util.List;

public interface ListarAcudientesUseCase {
    List<Acudiente> listar();
}
