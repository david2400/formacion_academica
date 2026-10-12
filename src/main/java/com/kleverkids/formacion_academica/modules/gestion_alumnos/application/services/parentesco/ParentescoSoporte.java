package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Lógica compartida por varios casos de uso de parentesco (antes métodos privados de
 * ParentescoService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class ParentescoSoporte {

    private final ParentescoRepositoryPort parentescoRepositoryPort;

    void validarNombreUnico(String nombre, Long excluirId) {
        if (excluirId == null) {
            if (parentescoRepositoryPort.existePorNombre(nombre)) {
                throw new IllegalArgumentException("Ya existe un parentesco con ese nombre");
            }
        } else {
            if (parentescoRepositoryPort.existePorNombreConIdDiferente(nombre, excluirId)) {
                throw new IllegalArgumentException("Ya existe otro parentesco con ese nombre");
            }
        }
    }
}
