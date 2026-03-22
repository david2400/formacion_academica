package com.kleverkids.formacion_academica.modules.control_academico.application.input.actividad;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.ProgresoActividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.RegistrarProgresoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.ActualizarProgresoDto;

public interface GestionarProgresoActividadUseCase {
    
    void registrarInicio(Long actividadId, Long estudianteId);
    void registrarCompletado(Long actividadId, Long estudianteId, RegistrarProgresoDto resultado);
    void actualizarProgreso(Long actividadId, Long estudianteId, ActualizarProgresoDto progreso);
    ProgresoActividad obtenerProgreso(Long actividadId);
    ProgresoActividad obtenerProgresoEstudiante(Long actividadId, Long estudianteId);
}
