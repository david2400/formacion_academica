package com.kleverkids.formacion_academica.modules.estados.application.input.entidad;

import com.kleverkids.formacion_academica.modules.estados.domain.dto.CambioEstadoRequest;
import com.kleverkids.formacion_academica.modules.estados.domain.dto.EstadoDto;

import java.util.List;
import java.util.Optional;

public interface GestionarEntidadEstadoUseCase {
    void asignarEstadoInicial(String entidadTipo, Long entidadId, Long idModulo, Long idUsuarioCambio);
    void cambiarEstadoConValidacion(CambioEstadoRequest request);
    Optional<EstadoDto> obtenerEstadoActual(String entidadTipo, Long entidadId);
    List<EstadoDto> listarHistorialEstados(String entidadTipo, Long entidadId);
}
