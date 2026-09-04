package com.kleverkids.formacion_academica.modules.control_academico.application.input.tipo_clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.ActualizarTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.CrearTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.TipoClase;

import java.util.List;
import java.util.Optional;

/** CRUD del catálogo de tipos de clase. */
public interface GestionarTiposClaseUseCase {

    TipoClase crear(CrearTipoClaseDto request);

    Optional<TipoClase> consultarPorId(Long id);

    List<TipoClase> listar();

    TipoClase actualizar(Long id, ActualizarTipoClaseDto request);

    void eliminar(Long id);
}
