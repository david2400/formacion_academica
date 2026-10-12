package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.CrearParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.CrearParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearParentescoService implements CrearParentescoUseCase {

    private final ParentescoRepositoryPort parentescoRepositoryPort;
    private final ParentescoSoporte parentescoSoporte;

    @Override
    public Parentesco crear(CrearParentescoDto request) {
        parentescoSoporte.validarNombreUnico(request.getNombre(), null);
        return parentescoRepositoryPort.guardar(request);
    }
}
