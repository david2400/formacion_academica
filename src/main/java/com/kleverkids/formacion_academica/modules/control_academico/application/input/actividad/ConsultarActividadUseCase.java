package com.kleverkids.formacion_academica.modules.control_academico.application.input.actividad;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.FiltroActividadDto;

import java.util.List;
import java.util.Optional;

public interface ConsultarActividadUseCase {
    
    Optional<Actividad> consultarPorId(Long actividadId);
    List<Actividad> listar();
    List<Actividad> buscarPorCurso(Long cursoId);
    List<Actividad> buscarPorModulo(Long moduloId);
    List<Actividad> buscarConFiltro(FiltroActividadDto filtro);
    List<Actividad> buscarActividadesDisponibles(Long estudianteId);
}
