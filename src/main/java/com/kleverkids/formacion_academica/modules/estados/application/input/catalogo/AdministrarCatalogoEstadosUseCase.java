package com.kleverkids.formacion_academica.modules.estados.application.input.catalogo;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.ActualizarParametrizacionDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.CrearEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.HabilitarEstadoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.catalogo.RegistrarContextoDto;
import com.kleverkids.formacion_academica.modules.estados.domain.model.ContextoEstado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.Estado;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;

import java.util.List;
import java.util.Optional;

/**
 * Administración del catálogo de estados.
 *
 * <p>Separa <b>qué estados existen</b> (catálogo global, reutilizable) de
 * <b>dónde aplica cada uno</b> (parametrización por contexto). Así un mismo estado
 * —{@code activo}, {@code retirado}— se comparte entre módulos sin duplicarse, y
 * cada contexto define su propio inicial y sus propios terminales.
 */
public interface AdministrarCatalogoEstadosUseCase {

    List<Estado> listarEstados();

    Optional<Estado> consultarEstado(Long id);

    Optional<Estado> consultarEstadoPorCodigo(String codigo);

    Estado crearEstado(CrearEstadoDto request);

    Estado actualizarEstado(Long id, CrearEstadoDto request);

    /** Falla si el estado sigue habilitado en algún contexto. */
    void eliminarEstado(Long id);

    List<ContextoEstado> listarContextos();

    /** Idempotente: registrar la misma pareja módulo/entidad devuelve el existente. */
    ContextoEstado registrarContexto(RegistrarContextoDto request);

    EstadoContexto habilitarEstado(String codigoContexto, HabilitarEstadoDto request);

    EstadoContexto actualizarParametrizacion(Long id, ActualizarParametrizacionDto request);

    void deshabilitarEstado(Long id);
}
