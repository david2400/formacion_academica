package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ActualizarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ConsultarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.CrearGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.EliminarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ListarGruposUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GrupoService implements CrearGrupoUseCase, ActualizarGrupoUseCase,
        ConsultarGrupoUseCase, ListarGruposUseCase, EliminarGrupoUseCase {

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

    @Override
    public GrupoDto consultarPorId(UUID grupoId) {
        return grupoRepositoryPort.obtenerPorId(grupoId);
    }

    @Override
    public List<GrupoDto> listar() {
        return grupoRepositoryPort.listar();
    }

    @Override
    public void eliminar(UUID grupoId) {
        grupoRepositoryPort.obtenerPorId(grupoId);
        grupoRepositoryPort.eliminar(grupoId);
    }

    private void validarCodigoUnico(String codigo) {
        if (grupoRepositoryPort.existePorCodigo(codigo)) {
            throw new IllegalArgumentException("Ya existe un grupo con código " + codigo);
        }
    }
}
