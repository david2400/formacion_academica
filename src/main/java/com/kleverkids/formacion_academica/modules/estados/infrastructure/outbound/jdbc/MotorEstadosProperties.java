package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuración del acceso al motor de estados.
 *
 * <p>Prefijo {@code motor-estados} en application.properties.
 */
@ConfigurationProperties(prefix = "motor-estados")
public class MotorEstadosProperties {

    /**
     * Esquema donde vive el motor, en el mismo servidor MySQL que {@code academia}.
     *
     * <p>Es configurable y no una constante porque el nombre cambia entre entornos.
     * Se interpola en el SQL —no se puede parametrizar un nombre de esquema—, así que
     * <b>solo puede venir de la configuración del despliegue, nunca de una
     * petición</b>. El validador de abajo lo garantiza.
     */
    private String esquema = "security";

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        if (esquema == null || !esquema.matches("[A-Za-z0-9_]{1,64}")) {
            throw new IllegalArgumentException(
                    "motor-estados.esquema debe ser un identificador MySQL simple; recibido: " + esquema);
        }
        this.esquema = esquema;
    }
}
