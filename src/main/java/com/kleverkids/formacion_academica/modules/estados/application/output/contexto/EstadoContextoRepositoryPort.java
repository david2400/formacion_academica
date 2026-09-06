package com.kleverkids.formacion_academica.modules.estados.application.output.contexto;

import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida hacia el catálogo central de estados.
 *
 * <p>Es de solo lectura a propósito: la administración del catálogo y de la
 * parametrización vive en access_control. Esta aplicación consume, no configura.
 */
public interface EstadoContextoRepositoryPort {

    List<EstadoContexto> listarPorContexto(String contexto, Long idEmpresa);

    Optional<EstadoContexto> obtenerInicial(String contexto, Long idEmpresa);

    boolean estaRegistrado(String contexto, Long estadoId, Long idEmpresa);
}
