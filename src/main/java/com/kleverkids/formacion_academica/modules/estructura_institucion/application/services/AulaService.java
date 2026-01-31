package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ActualizarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.CrearAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ListarAulasUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.AulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.CrearAulaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AulaService implements CrearAulaUseCase, ActualizarAulaUseCase, ListarAulasUseCase {

    private final AulaRepositoryPort aulaRepositoryPort;

    public AulaService(AulaRepositoryPort aulaRepositoryPort) {
        this.aulaRepositoryPort = aulaRepositoryPort;
    }

    @Override
    public AulaDto crear(CrearAulaDto request) {
        validarNombreDisponible(request.nombre(), null);
        return aulaRepositoryPort.guardar(request);
    }

    @Override
    public AulaDto actualizar(ActualizarAulaDto request) {
        validarExistencia(request.id());
        validarNombreDisponible(request.nombre(), request.id());
        return aulaRepositoryPort.actualizar(request);
    }

    @Override
    public List<AulaDto> listar() {
        return aulaRepositoryPort.listar();
    }

    private void validarNombreDisponible(String nombre, UUID aulaId) {
        if (nombre == null || nombre.isBlank()) {
            return;
        }
        if (!aulaRepositoryPort.existePorNombre(nombre)) {
            return;
        }
        if (aulaId == null) {
            throw new IllegalArgumentException("Ya existe un aula con el nombre " + nombre);
        }
        AulaDto actual = aulaRepositoryPort.obtenerPorId(aulaId);
        if (!actual.nombre().equalsIgnoreCase(nombre)) {
            throw new IllegalArgumentException("Ya existe un aula con el nombre " + nombre);
        }
    }

    private void validarExistencia(UUID aulaId) {
        if (!aulaRepositoryPort.existePorId(aulaId)) {
            throw new IllegalArgumentException("El aula no existe");
        }
    }
}
