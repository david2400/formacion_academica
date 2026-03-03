package com.kleverkids.formacion_academica.modules.control_academico.application.service.asistencia;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.asistencia.EliminarAsistenciaUseCase;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarAsistenciaService implements EliminarAsistenciaUseCase {
    
    @Override
    public void eliminar(UUID asistenciaId) {
        // TODO: Implementar la lógica de eliminación de asistencia
        System.out.println("Eliminando asistencia con ID: " + asistenciaId);
    }
}
