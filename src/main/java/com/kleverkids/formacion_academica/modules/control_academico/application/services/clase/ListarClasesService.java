package com.kleverkids.formacion_academica.modules.control_academico.application.services.clase;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.clase.ListarClasesUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.clase.ClaseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.FiltroClasesDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarClasesService implements ListarClasesUseCase {

    private final ClaseRepositoryPort claseRepositoryPort;

    @Override
    public List<Clase> listarTodas() {
        return claseRepositoryPort.listarTodas();
    }

    @Override
    public List<Clase> buscar(FiltroClasesDto filtro) {
        if (filtro == null || filtro.estaVacio()) {
            return claseRepositoryPort.listarTodas();
        }
        return claseRepositoryPort.buscar(filtro);
    }
}
