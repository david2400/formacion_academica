package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.CrearExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen.RegistrarCalificacionPersonalizadaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import org.springframework.stereotype.Service;

@Service
public class ExamenService implements CrearExamenUseCase, RegistrarCalificacionPersonalizadaUseCase {

    private final ExamenRepositoryPort examenRepositoryPort;

    public ExamenService(ExamenRepositoryPort examenRepositoryPort) {
        this.examenRepositoryPort = examenRepositoryPort;
    }

    @Override
    public ExamenDto crear(CrearExamenDto request) {
        return examenRepositoryPort.guardar(request);
    }

    @Override
    public CalificacionPersonalizadaDto registrar(RegistrarCalificacionPersonalizadaDto request) {
        return examenRepositoryPort.registrarCalificacion(request);
    }
}
