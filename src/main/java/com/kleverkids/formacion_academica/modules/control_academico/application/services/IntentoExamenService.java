package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.FinalizarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.IniciarIntentoExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.ListarIntentosPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.RegistrarRespuestaIntentoUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.intento.IntentoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class IntentoExamenService implements IniciarIntentoExamenUseCase,
        RegistrarRespuestaIntentoUseCase,
        FinalizarIntentoExamenUseCase,
        ListarIntentosPorEstudianteUseCase {

    private final IntentoExamenRepositoryPort repositoryPort;

    public IntentoExamenService(IntentoExamenRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public IntentoExamenDto iniciar(IniciarIntentoExamenDto request) {
        return repositoryPort.iniciar(request);
    }

    @Override
    public RespuestaIntentoDto registrar(RegistrarRespuestaIntentoDto request) {
        return repositoryPort.registrarRespuesta(request);
    }

    @Override
    public IntentoExamenDto finalizar(FinalizarIntentoExamenDto request) {
        return repositoryPort.finalizar(request);
    }

    @Override
    public List<IntentoExamenDto> listar(UUID examenId, UUID estudianteId) {
        return repositoryPort.listarPorEstudiante(examenId, estudianteId);
    }
}
