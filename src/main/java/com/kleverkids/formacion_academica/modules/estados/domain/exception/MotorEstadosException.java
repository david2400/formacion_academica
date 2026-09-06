package com.kleverkids.formacion_academica.modules.estados.domain.exception;

/**
 * Fallo al operar contra el motor de estados de access_control.
 *
 * <p>Se distinguen dos causas porque tienen respuestas distintas: un problema de
 * configuración lo arregla un administrador en el motor; uno de disponibilidad se
 * resuelve solo cuando el servicio vuelve. Mezclarlas llevaría a diagnosticar mal
 * una caída como un dato mal cargado.
 */
public class MotorEstadosException extends RuntimeException {

    public enum Causa {
        /** La máquina, el estado o la transición no están donde deberían. */
        CONFIGURACION,
        /** El motor no responde o devuelve un error inesperado. */
        NO_DISPONIBLE,
        /** El motor rechazó la operación por sus propias reglas o permisos. */
        RECHAZADA
    }

    private final Causa causa;

    public MotorEstadosException(Causa causa, String mensaje) {
        super(mensaje);
        this.causa = causa;
    }

    public MotorEstadosException(Causa causa, String mensaje, Throwable origen) {
        super(mensaje, origen);
        this.causa = causa;
    }

    public Causa getCausa() {
        return causa;
    }

    public static MotorEstadosException configuracion(String mensaje) {
        return new MotorEstadosException(Causa.CONFIGURACION, mensaje);
    }

    public static MotorEstadosException noDisponible(String mensaje, Throwable origen) {
        return new MotorEstadosException(Causa.NO_DISPONIBLE, mensaje, origen);
    }

    public static MotorEstadosException rechazada(String mensaje) {
        return new MotorEstadosException(Causa.RECHAZADA, mensaje);
    }
}
