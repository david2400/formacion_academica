package com.kleverkids.formacion_academica.modules.control_academico.application.output.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ActualizarClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarSeguimientoClaseDto;

import java.util.List;
import java.util.Optional;


public interface ClaseRepositoryPort {

    Clase guardar(CrearClaseDto clase);

    List<Clase> guardarTodas(List<CrearClaseDto> clases);

    Clase getClaseById(Long id);

    Optional<Clase> obtenerPorId(Long id);

    List<Clase> listarTodas();

    Optional<Clase> buscarPorCodigo(String codigo);

    Clase actualizar(ActualizarClaseDto clase);

    Clase registrarSeguimiento(Long id, RegistrarSeguimientoClaseDto seguimiento);

    void eliminar(Long id);

    boolean existePorCodigo(String codigo);
}
