package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;

import java.util.List;
import java.util.UUID;

public interface AulaRepositoryPort {

    AulaDto guardar(CrearAulaDto request);

    AulaDto actualizar(ActualizarAulaDto request);

    boolean existePorNombre(String nombre);

    boolean existePorId(UUID id);

    AulaDto obtenerPorId(UUID id);

    List<AulaDto> listar();
}
