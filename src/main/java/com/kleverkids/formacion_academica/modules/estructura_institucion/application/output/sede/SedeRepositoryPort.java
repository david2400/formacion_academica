package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.CrearSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.ActualizarSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;

import java.util.List;
import java.util.Optional;

public interface SedeRepositoryPort {
    Sede registrar(CrearSedeDto request);
    Optional<Sede> obtenerPorId(Long id);
//    Optional<Sede> obtenerPorCodigo(String codigo);
    List<Sede> listarTodas();
    List<Sede> listarActivas();
//    List<Sede> listarPorCiudad(String ciudad);
    Optional<Sede> actualizar(Long id, ActualizarSedeDto request);
    boolean eliminar(Long id);
//    boolean existePorCodigo(String codigo);
}
