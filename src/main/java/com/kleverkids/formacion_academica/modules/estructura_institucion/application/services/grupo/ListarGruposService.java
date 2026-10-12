package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.ListarGruposUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarGruposService implements ListarGruposUseCase {

     private final GrupoRepositoryPort grupoRepositoryPort;

     @Override
     public List<Grupo> listar() {
         return grupoRepositoryPort.listar();
     }
}
