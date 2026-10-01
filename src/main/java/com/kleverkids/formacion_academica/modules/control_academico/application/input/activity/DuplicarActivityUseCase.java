package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;

/** Crea una copia DRAFT de la actividad (y su contenido actual), sin versiones. */
public interface DuplicarActivityUseCase {

    Activity duplicar(Long id);
}
