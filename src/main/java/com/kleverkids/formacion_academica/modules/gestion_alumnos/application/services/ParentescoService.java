package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ActualizarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ConsultarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.CrearParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.EliminarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ListarParentescosUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.ActualizarParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.CrearParentescoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParentescoService implements CrearParentescoUseCase,
        ActualizarParentescoUseCase,
        ConsultarParentescoUseCase,
        ListarParentescosUseCase,
        EliminarParentescoUseCase {

    private final ParentescoRepositoryPort parentescoRepositoryPort;

    public ParentescoService(ParentescoRepositoryPort parentescoRepositoryPort) {
        this.parentescoRepositoryPort = parentescoRepositoryPort;
    }

    @Override
    public Parentesco crear(CrearParentescoDto request) {
        validarNombreUnico(request.getNombre(), null);
        return parentescoRepositoryPort.guardar(request);
    }

    @Override
    public Parentesco actualizar(ActualizarParentescoDto request) {
        Parentesco existente = consultarPorId(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Parentesco no encontrado"));
        
        if (request.getNombre() != null && !request.getNombre().equals(existente.nombre())) {
            validarNombreUnico(request.getNombre(), request.getId());
        }
        
        return parentescoRepositoryPort.actualizar(request);
    }

    @Override
    public Optional<Parentesco> consultarPorId(Long id) {
        return parentescoRepositoryPort.obtenerPorId(id);
    }

    @Override
    public List<Parentesco> listarTodos() {
        return parentescoRepositoryPort.listarTodos();
    }

    @Override
    public List<Parentesco> listarActivos() {
        return parentescoRepositoryPort.listarActivos();
    }

    @Override
    public void eliminar(Long parentescoId) {
        consultarPorId(parentescoId)
                .orElseThrow(() -> new IllegalArgumentException("Parentesco no encontrado"));
        parentescoRepositoryPort.eliminar(parentescoId);
    }

    private void validarNombreUnico(String nombre, Long excluirId) {
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
