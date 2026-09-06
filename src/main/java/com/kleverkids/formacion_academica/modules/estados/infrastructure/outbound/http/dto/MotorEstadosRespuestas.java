package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Espejo de lo que devuelve el motor, reducido a lo que este cliente usa.
 *
 * <p>Se declaran solo los campos necesarios y todos ignoran los desconocidos: el
 * motor puede añadir campos a sus respuestas sin romper este servicio.
 *
 * <p><b>Cada campo lleva su {@code @JsonProperty} explícito, a propósito.</b> Los
 * dos servicios configuran {@code SNAKE_CASE}, pero no corren la misma versión de
 * Jackson —access_control va sobre Spring Boot 3.5 y este proyecto sobre Boot 4—, y
 * el nombre que cada una deduce de un componente booleano como {@code isInitial} no
 * coincide: una produce {@code is_initial} y la otra espera {@code initial}. Como
 * además está activo {@code fail-on-unknown-properties=false}, el desajuste no da
 * error: el campo llega nulo y la máquina parece no tener estado inicial. Fijar el
 * nombre elimina la dependencia de esa convención.
 */
public final class MotorEstadosRespuestas {

    private MotorEstadosRespuestas() {
    }

    /** {@code GET /state-machines/by-code/{code}} */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Maquina(
            @JsonProperty("id") Long id,
            @JsonProperty("code") String code,
            @JsonProperty("version") Integer version,
            @JsonProperty("status") String status,
            @JsonProperty("states") List<Estado> states) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Estado(
            @JsonProperty("id") Long id,
            @JsonProperty("code") String code,
            @JsonProperty("name") String name,
            @JsonProperty("is_initial") Boolean isInitial,
            @JsonProperty("is_final") Boolean isFinal) {
    }

    /** {@code GET /entities/{tipo}/{id}/state-machine} */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record VistaEstado(
            @JsonProperty("current_state") Estado currentState,
            @JsonProperty("available_actions") List<AccionDisponible> availableActions) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AccionDisponible(
            @JsonProperty("code") String code,
            @JsonProperty("name") String name,
            @JsonProperty("requires_reason") Boolean requiresReason,
            @JsonProperty("target_state") String targetState) {
    }

    /** {@code POST /entities/{tipo}/{id}/state-machine/transition} */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ResultadoTransicion(
            @JsonProperty("previous_state") Estado previousState,
            @JsonProperty("current_state") Estado currentState,
            @JsonProperty("action") String action) {
    }

    /** {@code POST /entities/{tipo}/{id}/state-machine/initialize} */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Instancia(
            @JsonProperty("id") Long id,
            @JsonProperty("current_state") Estado currentState) {
    }
}
