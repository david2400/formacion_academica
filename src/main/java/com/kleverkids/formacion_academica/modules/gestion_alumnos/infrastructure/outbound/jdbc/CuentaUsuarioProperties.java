package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.jdbc;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuración del acceso a la identidad centralizada (User/Client/Company)
 * de access_control.
 *
 * <p>Prefijo {@code access-control} en application.properties. Mismo estilo
 * que {@code MotorEstadosProperties} del módulo estados: ambos módulos leen
 * del mismo servidor MySQL, pero se configuran por separado porque son
 * capacidades distintas (identidad vs. máquina de estados) que podrían
 * evolucionar en momentos distintos.
 */
@ConfigurationProperties(prefix = "access-control")
public class CuentaUsuarioProperties {

    /**
     * Esquema donde vive access_control, en el mismo servidor MySQL que
     * {@code academia}.
     *
     * <p>Se interpola en el SQL —no se puede parametrizar un nombre de esquema—,
     * así que <b>solo puede venir de la configuración del despliegue, nunca de
     * una petición</b>. El validador de abajo lo garantiza.
     */
    private String esquema = "security";

    public String getEsquema() {
        return esquema;
    }

    public void setEsquema(String esquema) {
        if (esquema == null || !esquema.matches("[A-Za-z0-9_]{1,64}")) {
            throw new IllegalArgumentException(
                    "access-control.esquema debe ser un identificador MySQL simple; recibido: " + esquema);
        }
        this.esquema = esquema;
    }
}
