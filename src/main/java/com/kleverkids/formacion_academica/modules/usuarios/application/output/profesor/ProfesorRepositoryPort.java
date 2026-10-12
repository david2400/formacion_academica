package com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;

import java.util.Optional;

/** Escritura de profesores (tabla propia {@code profesores}). */
public interface ProfesorRepositoryPort {

    Profesor guardar(Profesor profesor);

    Optional<Profesor> buscarPorId(Long id);

    boolean existe(Long usuarioId, Long empresaId);

    /**
     * Si el usuario fue profesor de la empresa y se eliminó, lo recupera con los datos nuevos
     * (la restricción única usuario+empresa impide crear otra fila). Vacío si no había.
     */
    Optional<Long> restaurarEliminado(Profesor datos);

    void eliminar(Long id);
}
