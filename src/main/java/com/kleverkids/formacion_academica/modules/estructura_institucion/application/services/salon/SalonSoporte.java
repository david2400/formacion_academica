package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.salon;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.salon.SalonRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Salon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Lógica compartida por varios casos de uso de salon (antes métodos privados de
 * SalonService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class SalonSoporte {

    private final SalonRepositoryPort salonRepositoryPort;

    void validarCodigoDisponible(String codigo, Long salonId) {
        if (codigo == null || codigo.isBlank()) {
            return;
        }
        if (!salonRepositoryPort.existePorCodigo(codigo)) {
            return;
        }
        if (salonId == null) {
            throw new IllegalArgumentException("Ya existe un salón con el código " + codigo);
        }
        Salon actual = salonRepositoryPort.obtenerPorId(salonId);
        if (!actual.getCodigo().equalsIgnoreCase(codigo)) {
            throw new IllegalArgumentException("Ya existe un salón con el código " + codigo);
        }
    }

    void validarExistencia(Long salonId) {
        if (!salonRepositoryPort.existePorId(salonId)) {
            throw new IllegalArgumentException("El salón no existe");
        }
    }
}
