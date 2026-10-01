package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject;

/** Un item de secuencia es una Activity o un Grupo de actividades (un solo nivel
 * de anidamiento: un Grupo no puede contener otro Grupo — ver arquitectura
 * aprobada §2.3, nota de alcance). */
public enum TipoItemSecuencia {
    ACTIVITY,
    GROUP
}
