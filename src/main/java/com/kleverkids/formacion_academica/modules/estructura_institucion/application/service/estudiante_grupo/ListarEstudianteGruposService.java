package com.kleverkids.formacion_academica.modules.estructura_institucion.application.service.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudianteGruposUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ListarEstudianteGruposService implements ListarEstudianteGruposUseCase {
    
    @Override
    public List<EstudianteGrupoDto> listar() {
        // TODO: Implementar la lógica de listado de estudiante-grupos
        // Por ahora, retornamos una lista vacía para que la aplicación inicie
        return new ArrayList<>();
    }
}
