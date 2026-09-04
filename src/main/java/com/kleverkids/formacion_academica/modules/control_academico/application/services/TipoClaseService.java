package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tipo_clase.GestionarTiposClaseUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tipo_clase.TipoClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.ActualizarTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tipo_clase.CrearTipoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.TipoClase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TipoClaseService implements GestionarTiposClaseUseCase {

    private final TipoClaseRepositoryPort tipoClaseRepositoryPort;

    @Override
    public TipoClase crear(CrearTipoClaseDto request) {
        if (tipoClaseRepositoryPort.existePorNombre(request.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe un tipo de clase con el nombre: " + request.getNombre());
        }
        return tipoClaseRepositoryPort.guardar(request);
    }

    @Override
    public Optional<TipoClase> consultarPorId(Long id) {
        return tipoClaseRepositoryPort.obtenerPorId(id);
    }

    @Override
    public List<TipoClase> listar() {
        return tipoClaseRepositoryPort.listar();
    }

    @Override
    public TipoClase actualizar(Long id, ActualizarTipoClaseDto request) {
        return tipoClaseRepositoryPort.actualizar(id, request);
    }

    @Override
    public void eliminar(Long id) {
        tipoClaseRepositoryPort.eliminar(id);
    }
}
