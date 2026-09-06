package com.kleverkids.formacion_academica.modules.estados.domain.model;

import com.kleverkids.formacion_academica.modules.estados.domain.exception.MotorEstadosException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Definición de una máquina tal como la publicó el motor.
 *
 * <p>Es inmutable a propósito: una máquina PUBLISHED no cambia: evolucionar el
 * grafo crea una versión nueva. Eso es lo que permite cachearla sin miedo.
 */
public record MaquinaEstados(
        Long id,
        String codigo,
        Integer version,
        List<EstadoMotor> estados) {

    public MaquinaEstados {
        estados = estados == null ? List.of() : List.copyOf(estados);
    }

    /**
     * El estado inicial. Falla si no hay exactamente uno: el motor valida esa regla
     * al publicar, así que incumplirla significa que la caché quedó obsoleta o que
     * alguien tocó la base por debajo. En cualquier caso es mejor parar que asignar
     * un estado arbitrario.
     */
    public Long estadoInicial() {
        List<EstadoMotor> iniciales = estados.stream().filter(EstadoMotor::inicial).toList();
        if (iniciales.size() != 1) {
            throw MotorEstadosException.configuracion(
                    "La máquina '" + codigo + "' tiene " + iniciales.size()
                            + " estados iniciales y debería tener exactamente uno");
        }
        return iniciales.get(0).id();
    }

    public boolean contiene(Long estadoId) {
        return estadoId != null && estados.stream().anyMatch(e -> estadoId.equals(e.id()));
    }

    public Optional<EstadoMotor> porId(Long estadoId) {
        return estados.stream().filter(e -> e.id().equals(estadoId)).findFirst();
    }

    public Optional<EstadoMotor> porCodigo(String codigoEstado) {
        return estados.stream().filter(e -> e.codigo().equals(codigoEstado)).findFirst();
    }

    /** Índice código -> id, para traducir lo que devuelve el motor a la columna local. */
    public Map<String, Long> idsPorCodigo() {
        return estados.stream().collect(Collectors.toMap(EstadoMotor::codigo, EstadoMotor::id));
    }

    public Map<Long, String> codigosPorId() {
        return estados.stream().collect(Collectors.toMap(EstadoMotor::id, EstadoMotor::codigo));
    }

    /** Solo para mensajes de error legibles. */
    public String codigosDisponibles() {
        return estados.stream().map(EstadoMotor::codigo).collect(Collectors.joining(", "));
    }
}
