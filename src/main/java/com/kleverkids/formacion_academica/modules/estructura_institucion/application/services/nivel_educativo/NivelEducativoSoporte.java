package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.nivel_educativo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.nivel_educativo.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.nivel.NivelEducativoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.NivelEducativo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Lógica compartida por varios casos de uso de nivel_educativo (antes métodos privados de
 * NivelEducativoService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class NivelEducativoSoporte {

    private final NivelEducativoRepositoryPort repository;

    void validarNivelSuperior(Long nivelSuperiorId) {
        if (nivelSuperiorId != null) {
            Optional<NivelEducativo> nivelSuperior = repository.buscarPorId(nivelSuperiorId);
            if (nivelSuperior.isEmpty()) {
                throw new IllegalArgumentException("El nivel superior especificado no existe");
            }
        }
    }
}
