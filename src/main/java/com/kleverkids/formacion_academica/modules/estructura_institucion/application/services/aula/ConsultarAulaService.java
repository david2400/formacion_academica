package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.aula;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.aula.ConsultarAulaUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.aula.AulaRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Aula;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarAulaService implements ConsultarAulaUseCase {

    private final AulaRepositoryPort aulaRepositoryPort;

    @Override
    public Aula consultarPorId(Long aulaId) {
        return aulaRepositoryPort.obtenerPorId(aulaId);
    }
}
