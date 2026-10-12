package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ActualizarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ConsultarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.ActualizarParentescoDto;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarParentescoService implements ActualizarParentescoUseCase {

    private final ParentescoRepositoryPort parentescoRepositoryPort;
    private final ParentescoSoporte parentescoSoporte;
    private final ConsultarParentescoUseCase consultarParentescoUseCase;

    @Override
    public Parentesco actualizar(ActualizarParentescoDto request) {
        Parentesco existente = consultarParentescoUseCase.consultarPorId(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Parentesco no encontrado"));
        
        if (request.getNombre() != null && !request.getNombre().equals(existente.getNombre())) {
            parentescoSoporte.validarNombreUnico(request.getNombre(), request.getId());
        }
        
        return parentescoRepositoryPort.actualizar(request);
    }
}
