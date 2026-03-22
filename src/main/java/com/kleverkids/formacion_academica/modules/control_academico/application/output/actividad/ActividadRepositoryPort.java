package com.kleverkids.formacion_academica.modules.control_academico.application.output.actividad;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.ProgresoActividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.CrearActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.ActualizarActividadDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.FiltroActividadDto;

import java.util.List;
import java.util.Optional;

public interface ActividadRepositoryPort {
    
    // Operaciones CRUD básicas
    Actividad guardar(CrearActividadDto request);
    Actividad actualizar(ActualizarActividadDto request);
    Optional<Actividad> obtenerPorId(Long id);
    List<Actividad> listar();
    void eliminar(Long id);
    
    // Operaciones de búsqueda y filtrado
    List<Actividad> buscarPorCurso(Long cursoId);
    List<Actividad> buscarPorModulo(Long moduloId);
    List<Actividad> buscarPorTipo(String tipoActividad);
    List<Actividad> buscarConFiltro(FiltroActividadDto filtro);
    
    // Operaciones de progreso
    ProgresoActividad obtenerProgreso(Long actividadId);
    void guardarProgreso(ProgresoActividad progreso);
    
    // Operaciones de estado
    void actualizarEstado(Long actividadId, String nuevoEstado);
    List<Actividad> buscarActividadesDisponibles(Long estudianteId);
    
    // Operaciones de dependencias
    List<Actividad> buscarDependencias(Long actividadId);
    List<Actividad> buscarActividadesQueDependen(Long actividadId);
    
    // Operaciones de evaluación
    List<Actividad> buscarPorEvaluacionRequerida(Boolean requiereEvaluacion);
    
    // Operaciones de estadísticas
    Long contarActividadesPorCurso(Long cursoId);
    Long contarActividadesActivasPorCurso(Long cursoId);
    
    // Operaciones de ordenamiento
    List<Actividad> listarPorOrden(Long cursoId, Long moduloId);
    void actualizarOrden(Long actividadId, Integer nuevoOrden);
    
    // Validaciones de dominio
    boolean existePorTituloYCurso(String titulo, Long cursoId);
    boolean existeDependenciaCiclica(Long actividadId, List<Long> dependencias);
}
