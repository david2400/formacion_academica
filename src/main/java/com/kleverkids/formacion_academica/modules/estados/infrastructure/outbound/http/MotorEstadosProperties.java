package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Configuración del cliente del motor de estados.
 *
 * <p>Prefijo {@code motor-estados} en application.properties.
 */
@ConfigurationProperties(prefix = "motor-estados")
public class MotorEstadosProperties {

    /** Base del API de access_control, incluyendo su prefijo. */
    private String baseUrl = "http://localhost:8000/api/access_control";

    /**
     * Corte de la conexión. Deliberadamente bajo: este cliente se invoca dentro de
     * transacciones de escritura, y una espera larga mantiene bloqueos abiertos en
     * la base de datos mucho más tiempo del que costaría fallar y reintentar.
     */
    private Duration connectTimeout = Duration.ofSeconds(3);

    private Duration readTimeout = Duration.ofSeconds(5);

    /**
     * Cuánto se conserva en caché la definición de una máquina.
     *
     * <p>Una máquina publicada es inmutable, así que el riesgo no es que cambie sino
     * que se publique una versión nueva y este servicio siga usando la anterior.
     * Diez minutos acota esa ventana sin convertir cada creación en una llamada
     * remota.
     */
    private Duration cacheTtl = Duration.ofMinutes(10);

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Duration getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Duration getCacheTtl() {
        return cacheTtl;
    }

    public void setCacheTtl(Duration cacheTtl) {
        this.cacheTtl = cacheTtl;
    }
}
