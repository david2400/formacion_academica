package com.kleverkids.formacion_academica.modules.control_academico.application.services.intento;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.intento.ListarIntentosPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.intento.IntentoExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarIntentosPorEstudianteService implements ListarIntentosPorEstudianteUseCase {

    private final IntentoExamenRepositoryPort repositoryPort;

    @Override
    public List<IntentoExamenDto> listar(Long examenId, Long estudianteId) {
        return repositoryPort.listarPorEstudiante(examenId, estudianteId);
    }
}
