package com.kleverkids.formacion_academica.modules.estructura_institucion.application.service.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.EliminarEstudianteGrupoUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarEstudianteGrupoService implements EliminarEstudianteGrupoUseCase {
    
    @Override
    public void eliminar(UUID estudianteGrupoId) {
        // TODO: Implementar la lógica de eliminación de estudiante-grupo
        System.out.println("Eliminando estudiante-grupo con ID: " + estudianteGrupoId);
    }
}
