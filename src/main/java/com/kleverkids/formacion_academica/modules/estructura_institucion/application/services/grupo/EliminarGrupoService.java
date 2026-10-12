package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo.EliminarGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.grupo.GrupoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarGrupoService implements EliminarGrupoUseCase {

     private final GrupoRepositoryPort grupoRepositoryPort;

     @Override
     public void eliminar(Long grupoId) {
         grupoRepositoryPort.obtenerPorId(grupoId);
         grupoRepositoryPort.eliminar(grupoId);
     }
}
