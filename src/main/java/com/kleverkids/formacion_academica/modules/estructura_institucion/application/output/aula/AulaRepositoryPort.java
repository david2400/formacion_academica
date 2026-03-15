package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;

import java.util.List;

public interface AulaRepositoryPort {

    AulaDto guardar(CrearAulaDto request);

    AulaDto actualizar(ActualizarAulaDto request);

    boolean existePorNombre(String nombre);

    boolean existePorId(Long id);

    AulaDto obtenerPorId(Long id);

    List<AulaDto> listar();

    void eliminar(Long id);
}
