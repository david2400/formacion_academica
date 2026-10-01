package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;

/**
 * Publica una Activity: valida (vía el ActivityHandler de su tipo) que tenga
 * contenido válido, congela una ActivityVersion nueva y marca la actividad
 * como PUBLISHED. Ver arquitectura aprobada, §2.7 (versionamiento).
 */
public interface PublicarActivityUseCase {

    Activity publicar(Long id);
}
