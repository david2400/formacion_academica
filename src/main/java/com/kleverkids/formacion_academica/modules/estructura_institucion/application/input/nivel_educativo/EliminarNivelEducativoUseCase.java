package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo;

public interface EliminarNivelEducativoUseCase {

    /** @return false si el nivel no existe. */
    boolean eliminar(Long id);
}
