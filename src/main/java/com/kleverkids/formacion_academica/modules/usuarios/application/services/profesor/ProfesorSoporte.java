package com.kleverkids.formacion_academica.modules.usuarios.application.services.profesor;

import com.kleverkids.formacion_academica.modules.usuarios.application.input.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.application.output.profesor.ProfesorRepositoryPort;
import com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor.*;
import com.kleverkids.formacion_academica.modules.usuarios.domain.model.Profesor;
import com.kleverkids.formacion_academica.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Lógica compartida por varios casos de uso de profesor (antes métodos privados de
 * ProfesorService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class ProfesorSoporte {

    /** Tope de resultados del buscador de usuarios y del resumen por lote. */
    static final int LIMITE_MAXIMO = 50;
    private final ProfesorRepositoryPort repository;

    Profesor existente(Long id) {
        return repository.buscarPorId(id).orElseThrow(() -> new NotFoundException("Profesor no encontrado."));
    }

    static String limpiar(String texto) {
        if (texto == null) {
            return null;
        }
        String t = texto.trim();
        return t.isEmpty() ? null : t;
    }
}
