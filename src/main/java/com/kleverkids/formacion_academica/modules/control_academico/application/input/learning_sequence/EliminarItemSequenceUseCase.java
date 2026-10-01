package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

public interface EliminarItemSequenceUseCase {

    /** Si el item es un GROUP, también elimina los items hijos (cascada de un solo nivel). */
    void eliminarItem(Long sequenceId, Long itemId);
}
