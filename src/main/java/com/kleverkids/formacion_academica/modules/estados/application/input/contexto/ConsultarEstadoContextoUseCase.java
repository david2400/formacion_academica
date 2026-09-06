package com.kleverkids.formacion_academica.modules.estados.application.input.contexto;

import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;

import java.util.List;
import java.util.Optional;

/**
 * Consulta del catálogo de estados aplicable a un contexto.
 *
 * <p>Es el puerto que consumen los demás módulos para resolver y validar estados
 * sin depender de identificadores fijos. Detrás hay una llamada al servicio
 * access_control, cacheada.
 */
public interface ConsultarEstadoContextoUseCase {

    /** Estados habilitados para el contexto, ordenados. */
    List<EstadoContexto> listarPorContexto(String contexto, Long idEmpresa);

    /** Estado que se asigna al crear una entidad de este contexto. */
    Optional<EstadoContexto> obtenerInicial(String contexto, Long idEmpresa);

    /** Id del estado inicial; falla con mensaje explícito si no está parametrizado. */
    Long requerirEstadoInicial(String contexto, Long idEmpresa);

    /** Valida que un estado esté habilitado en el contexto antes de persistirlo. */
    boolean estaRegistrado(String contexto, Long estadoId, Long idEmpresa);
}
