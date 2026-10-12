package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.services.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.ConsultarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco.EliminarParentescoUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.parentesco.ParentescoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarParentescoService implements EliminarParentescoUseCase {

    private final ParentescoRepositoryPort parentescoRepositoryPort;
    private final ConsultarParentescoUseCase consultarParentescoUseCase;

    @Override
    public void eliminar(Long parentescoId) {
        consultarParentescoUseCase.consultarPorId(parentescoId)
                .orElseThrow(() -> new IllegalArgumentException("Parentesco no encontrado"));
        parentescoRepositoryPort.eliminar(parentescoId);
    }
}
