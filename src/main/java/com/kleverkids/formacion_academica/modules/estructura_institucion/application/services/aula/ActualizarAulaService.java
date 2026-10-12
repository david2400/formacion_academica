package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ActualizarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula.ActualizarAulaDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarAulaService implements ActualizarAulaUseCase {

    private final AulaRepositoryPort aulaRepositoryPort;

    @Override
    public Aula actualizar(ActualizarAulaDto request) {
        return aulaRepositoryPort.actualizar(request);
    }
}
