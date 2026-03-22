package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

import java.time.Duration;
import java.util.Objects;

public record ConfiguracionTiempo(
    Duration duracion,
    boolean tieneLimite,
    boolean permiteExtension,
    Duration extensionMaxima
) {
    
    public ConfiguracionTiempo {
        Objects.requireNonNull(duracion, "Duración no puede ser nula");
        if (tieneLimite && duracion.isNegative() || duracion.isZero()) {
            throw new IllegalArgumentException("Duración debe ser positiva cuando hay límite");
        }
        if (permiteExtension && (extensionMaxima == null || extensionMaxima.isNegative())) {
            throw new IllegalArgumentException("Extensión máxima debe ser positiva cuando se permite extensión");
        }
    }
    
    public static ConfiguracionTiempo sinLimite() {
        return new ConfiguracionTiempo(Duration.ZERO, false, false, Duration.ZERO);
    }
    
    public static ConfiguracionTiempo conLimite(Duration duracion) {
        return new ConfiguracionTiempo(duracion, true, false, Duration.ZERO);
    }
    
    public static ConfiguracionTiempo conExtension(Duration duracion, Duration extensionMaxima) {
        return new ConfiguracionTiempo(duracion, true, true, extensionMaxima);
    }
    
    public Duration getTiempoTotal() {
        return tieneLimite && permiteExtension ? duracion.plus(extensionMaxima) : duracion;
    }
    
    public boolean estaExcedido(Duration tiempoTranscurrido) {
        return tieneLimite && tiempoTranscurrido.compareTo(getTiempoTotal()) > 0;
    }
    
    public Duration getTiempoRestante(Duration tiempoTranscurrido) {
        if (!tieneLimite) {
            return Duration.ZERO;
        }
        
        Duration total = getTiempoTotal();
        Duration restante = total.minus(tiempoTranscurrido);
        return restante.isNegative() ? Duration.ZERO : restante;
    }
}
