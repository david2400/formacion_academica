package com.kleverkids.formacion_academica.modules.estados.application.services;

import com.kleverkids.formacion_academica.modules.estados.application.input.contexto.ConsultarEstadoContextoUseCase;
import com.kleverkids.formacion_academica.modules.estados.application.output.contexto.EstadoContextoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstadoContextoService implements ConsultarEstadoContextoUseCase {

    private final EstadoContextoRepositoryPort repositoryPort;

    @Override
    public List<EstadoContexto> listarPorContexto(String contexto, Long idEmpresa) {
        return repositoryPort.listarPorContexto(contexto, idEmpresa);
    }

    @Override
    public Optional<EstadoContexto> obtenerInicial(String contexto, Long idEmpresa) {
        return repositoryPort.obtenerInicial(contexto, idEmpresa);
    }

    @Override
    public Long requerirEstadoInicial(String contexto, Long idEmpresa) {
        return obtenerInicial(contexto, idEmpresa)
                .map(EstadoContexto::getEstadoId)
                .orElseThrow(() -> new IllegalStateException(
                        "El contexto '" + contexto + "' no tiene un estado inicial parametrizado en access_control. "
                                + "Regístralo en /api/access_control/estados/contextos y marca un estado como inicial."));
    }

    @Override
    public boolean estaRegistrado(String contexto, Long estadoId, Long idEmpresa) {
        return repositoryPort.estaRegistrado(contexto, estadoId, idEmpresa);
    }
}
