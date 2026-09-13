package com.kleverkids.formacion_academica.modules.estados.domain.model;

/**
 * Un estado dentro de una máquina, tal como lo necesita la UI.
 *
 * <p>El {@code id} es el que se replica en la columna {@code estado_id} de las
 * tablas de negocio y el que viaja en {@code nuevo_estado_id}. Vive en el esquema
 * {@code security}, así que no hay ni puede haber llave foránea hacia él.
 *
 * <p>{@code color} e {@code icono} salen de la metadata del estado. Son
 * presentación: existen para que cambiar el color de un estado no obligue a
 * desplegar el frontend.
 */
public record EstadoMotor(
        Long id,
        String codigo,
        String nombre,
        String descripcion,
        String color,
        String icono,
        boolean esInicial,
        boolean esFinal,
        int orden) {
}
