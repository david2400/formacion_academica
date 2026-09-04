package com.kleverkids.formacion_academica.modules.control_academico.application.output.tipo_clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.ActualizarTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.CrearTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.TipoClase;

import java.util.List;
import java.util.Optional;

public interface TipoClaseRepositoryPort {

    TipoClase guardar(CrearTipoClaseDto tipo);

    Optional<TipoClase> obtenerPorId(Long id);

    List<TipoClase> listar();

    TipoClase actualizar(Long id, ActualizarTipoClaseDto tipo);

    void eliminar(Long id);

    boolean existePorNombre(String nombre);
}
