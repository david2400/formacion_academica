package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;

import java.util.List;

public interface AulaRepositoryPort {

    Aula guardar(CrearAulaDto request);

    Aula actualizar(ActualizarAulaDto request);

    boolean existePorNombre(String nombre);

    boolean existePorId(Long id);

    Aula obtenerPorId(Long id);

    List<Aula> listar();

    void eliminar(Long id);
}
