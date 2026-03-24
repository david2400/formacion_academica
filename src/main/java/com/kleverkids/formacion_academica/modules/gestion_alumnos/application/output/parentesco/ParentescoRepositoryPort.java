package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.CrearParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.ActualizarParentescoDto;

import java.util.List;
import java.util.Optional;

public interface ParentescoRepositoryPort {
    Parentesco guardar(CrearParentescoDto request);
    Parentesco actualizar(ActualizarParentescoDto request);
    Optional<Parentesco> obtenerPorId(Long parentescoId);
    List<Parentesco> listarTodos();
    List<Parentesco> listarActivos();
    boolean existePorNombre(String nombre);
    boolean existePorNombreConIdDiferente(String nombre, Long id);
    void eliminar(Long parentescoId);
}
