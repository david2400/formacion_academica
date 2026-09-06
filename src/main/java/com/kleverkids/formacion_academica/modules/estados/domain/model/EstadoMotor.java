package com.kleverkids.formacion_academica.modules.estados.domain.model;

/**
 * Un estado dentro de una máquina.
 *
 * <p>El {@code id} es el que se replica en la columna {@code estado_id} de las
 * tablas de negocio. Es un id de la base {@code security}, no de {@code academia}:
 * no hay llave foránea posible entre las dos, así que la integridad la sostiene el
 * motor, no el motor de base de datos.
 */
public record EstadoMotor(
        Long id,
        String codigo,
        String nombre,
        boolean inicial,
        boolean finalizador) {
}
