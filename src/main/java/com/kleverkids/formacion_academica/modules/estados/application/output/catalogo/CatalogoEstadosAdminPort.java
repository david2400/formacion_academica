package com.kleverkids.formacion_academica.modules.estados.application.output.catalogo;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.ActualizarParametrizacionDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.CrearEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.HabilitarEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.RegistrarContextoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.model.ContextoEstado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.Estado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;

import java.util.List;
import java.util.Optional;

/** Puerto de salida para administrar el catálogo, los contextos y su parametrización. */
public interface CatalogoEstadosAdminPort {

    // Catálogo
    List<Estado> listarEstados();

    Optional<Estado> buscarEstadoPorId(Long id);

    Optional<Estado> buscarEstadoPorCodigo(String codigo);

    Estado crearEstado(CrearEstadoDto request);

    Estado actualizarEstado(Long id, CrearEstadoDto request);

    void eliminarEstado(Long id);

    // Contextos
    List<ContextoEstado> listarContextos();

    ContextoEstado registrarContexto(RegistrarContextoDto request);

    // Parametrización
    EstadoContexto habilitarEstado(String codigoContexto, HabilitarEstadoDto request);

    EstadoContexto actualizarParametrizacion(Long id, ActualizarParametrizacionDto request);

    void deshabilitarEstado(Long id);
}
