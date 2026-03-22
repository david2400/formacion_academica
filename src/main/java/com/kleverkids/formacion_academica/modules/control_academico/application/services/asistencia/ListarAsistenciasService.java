package com.kleverkids.formacion_academica.modules.control_academico.application.services.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.ListarAsistenciasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia.AsistenciaDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Service
public class ListarAsistenciasService implements ListarAsistenciasUseCase {
    
    @Override
    public List<AsistenciaDto> listar() {
        // TODO: Implementar la lógica de listado de asistencias
        // Por ahora, retornamos una lista vacía para que la aplicación inicie
        return new ArrayList<>();
    }
}
