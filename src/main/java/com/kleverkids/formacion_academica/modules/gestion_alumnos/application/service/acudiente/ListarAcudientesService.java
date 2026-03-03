package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.service.acudiente;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.acudiente.ListarAcudientesUseCase;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente.AcudienteDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class ListarAcudientesService implements ListarAcudientesUseCase {
    
    @Override
    public List<AcudienteDto> listar() {
        // TODO: Implementar la lógica de listado de acudientes
        // Por ahora, retornamos una lista vacía para que la aplicación inicie
        return new ArrayList<>();
    }
}
