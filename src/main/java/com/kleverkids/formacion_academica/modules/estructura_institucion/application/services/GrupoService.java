package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ActualizarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.CrearGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import org.springframework.stereotype.Service;

@Service
public class GrupoService implements CrearGrupoUseCase, ActualizarGrupoUseCase {

    private final GrupoRepositoryPort grupoRepositoryPort;

    public GrupoService(GrupoRepositoryPort grupoRepositoryPort) {
        this.grupoRepositoryPort = grupoRepositoryPort;
    }

    @Override
    public GrupoDto crear(CrearGrupoDto request) {
        validarCodigoUnico(request.codigo());
        return grupoRepositoryPort.guardar(request);
    }

    @Override
    public GrupoDto actualizar(ActualizarGrupoDto request) {
        return grupoRepositoryPort.actualizar(request);
    }

    private void validarCodigoUnico(String codigo) {
        if (grupoRepositoryPort.existePorCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un grupo con código " + codigo);
        }
    }
}
