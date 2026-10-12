package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede.*;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.sede.SedeRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarSedesService implements ListarSedesUseCase {

    private final SedeRepositoryPort sedeRepositoryPort;

//    @Override
//    public Optional<Sede> buscarPorCodigo(String codigo) {
//        return sedeRepositoryPort.obtenerPorCodigo(codigo);
//    }

    @Override
    public List<Sede> listarTodas() {
        return sedeRepositoryPort.listarTodas();
    }

    @Override
    public List<Sede> listarActivas() {
        return sedeRepositoryPort.listarActivas();
    }
}
