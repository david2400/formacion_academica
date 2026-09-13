package com.kleverkids.formacion_academica.modules.estados.domain.model;

/**
 * Una arista del grafo: desde qué estado, con qué acción, hacia cuál.
 *
 * <p>Se expone el grafo completo de la máquina en vez de "los destinos de esta
 * entidad" porque las pantallas son tablas: con lo segundo, pintar una lista de 50
 * filas serían 50 peticiones. Con el grafo, el cliente calcula los destinos de
 * cualquier fila a partir del {@code estado_id} que ya trae.
 *
 * <p>Es seguro hacerlo en el cliente porque quien decide sigue siendo el backend:
 * esto solo evita ofrecer opciones que se van a rechazar.
 */
public record TransicionMotor(
        Long desdeId,
        String desde,
        Long hastaId,
        String hasta,
        String hastaNombre,
        String hastaColor,
        boolean hastaEsFinal,
        String accion,
        String accionNombre,
        /** Si es {@code true}, el backend rechaza la transición sin motivo. */
        boolean exigeMotivo) {
}
