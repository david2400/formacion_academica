package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.CrearAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearAulaService implements CrearAulaUseCase {

    private final AulaRepositoryPort aulaRepositoryPort;

    @Override
    public Aula crear(CrearAulaDto request) {
        validarNombreDisponible(request.getNombre(), null);
        return aulaRepositoryPort.guardar(request);
    }

    private void validarNombreDisponible(String nombre, Long aulaId) {
        if (nombre == null || nombre.isBlank()) {
            return;
        }
        if (!aulaRepositoryPort.existePorNombre(nombre)) {
            return;
        }
        if (aulaId == null) {
            throw new IllegalArgumentException("Ya existe un aula con el nombre " + nombre);
        }
        Aula actual = aulaRepositoryPort.obtenerPorId(aulaId);
        if (!actual.getNombre().equalsIgnoreCase(nombre)) {
            throw new IllegalArgumentException("Ya existe un aula con el nombre " + nombre);
        }
    }
}
