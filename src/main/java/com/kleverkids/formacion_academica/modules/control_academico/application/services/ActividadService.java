package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.actividad.*;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.actividad.ActividadRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.Actividad;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActividadService implements 
    CrearActividadUseCase, 
    ActualizarActividadUseCase, 
    ConsultarActividadUseCase,
    GestionarProgresoActividadUseCase {

    private final ActividadRepositoryPort actividadRepository;

    public ActividadService(ActividadRepositoryPort actividadRepository) {
        this.actividadRepository = actividadRepository;
    }

    @Override
    public Actividad crear(CrearActividadDto request) {
        validarCreacionActividad(request);
        
        // Convertir DTO a domain model
        Actividad actividad = convertirDtoToActividad(request);
        
        // Validar dependencias si existen
        // if (actividad.getActividadesDependientes() != null && !actividad.getActividadesDependientes().isEmpty()) {
        //     validarDependencias(actividad.getId(), actividad.getActividadesDependientes());
        // }
        
        // Guardar actividad
        return actividadRepository.guardar(request);
    }

    @Override
    public Actividad actualizar(ActualizarActividadDto request) {
        // Verificar que la actividad existe
        Optional<Actividad> existente = actividadRepository.obtenerPorId(request.getId());
        if (existente.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada con ID: " + request.getId());
        }
        
        // Validar que se puede actualizar
        Actividad actividad = existente.get();
        // if (!actividad.getConfiguracion().esEditable()) {
        //     throw new IllegalStateException("La actividad no se puede editar en su estado actual");
        // }
        
        // Validar dependencias si se actualizaron
        // if (request.getActividadesDependientes() != null) {
        //     validarDependencias(request.getId(), request.getActividadesDependientes());
        // }
        
        return actividadRepository.actualizar(request);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Actividad> consultarPorId(Long actividadId) {
        return actividadRepository.obtenerPorId(actividadId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> listar() {
        return actividadRepository.listar();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarPorCurso(Long cursoId) {
        return actividadRepository.buscarPorCurso(cursoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarPorModulo(Long moduloId) {
        return actividadRepository.buscarPorModulo(moduloId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarConFiltro(FiltroActividadDto filtro) {
        return actividadRepository.buscarConFiltro(filtro);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Actividad> buscarActividadesDisponibles(Long estudianteId) {
        return actividadRepository.buscarActividadesDisponibles(estudianteId);
    }

    @Override
    public void registrarInicio(Long actividadId, Long estudianteId) {
        Optional<Actividad> actividadOpt = actividadRepository.obtenerPorId(actividadId);
        if (actividadOpt.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada: " + actividadId);
        }
        
        Actividad actividad = actividadOpt.get();
        if (!actividad.estaDisponiblePara(estudianteId)) {
            throw new IllegalStateException("La actividad no está disponible para el estudiante");
        }
        
        ProgresoActividad progreso = actividadRepository.obtenerProgreso(actividadId);
        progreso.registrarInicio(estudianteId);
        actividadRepository.guardarProgreso(progreso);
    }

    @Override
    public void registrarCompletado(Long actividadId, Long estudianteId, RegistrarProgresoDto resultado) {
        Optional<Actividad> actividadOpt = actividadRepository.obtenerPorId(actividadId);
        if (actividadOpt.isEmpty()) {
            throw new IllegalArgumentException("Actividad no encontrada: " + actividadId);
        }
        
        Actividad actividad = actividadOpt.get();
        
        // Convertir DTO a resultado de dominio
        ResultadoActividad resultadoDominio = convertirDtoToResultado(estudianteId, actividadId, resultado);
        
        // Registrar completado en la actividad - Comentado porque el método fue eliminado (records inmutables)
        // actividad.completar(estudianteId, resultadoDominio);
        
        // Actualizar progreso directamente en el repositorio
        ProgresoActividad progreso = actividadRepository.obtenerProgreso(actividadId);
        progreso.registrarCompletado(estudianteId, resultadoDominio);
        actividadRepository.guardarProgreso(progreso);
    }

    @Override
    public void actualizarProgreso(Long actividadId, Long estudianteId, ActualizarProgresoDto progreso) {
        ProgresoActividad progresoActividad = actividadRepository.obtenerProgreso(actividadId);
        progresoActividad.actualizarPorcentaje(estudianteId, progreso.getPorcentajeAvance());
        actividadRepository.guardarProgreso(progresoActividad);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgresoActividad obtenerProgreso(Long actividadId) {
        return actividadRepository.obtenerProgreso(actividadId);
    }

    @Override
    @Transactional(readOnly = true)
    public ProgresoActividad obtenerProgresoEstudiante(Long actividadId, Long estudianteId) {
        ProgresoActividad progreso = actividadRepository.obtenerProgreso(actividadId);
        // Filtrar solo el progreso del estudiante específico
        // Esto podría requerir un método adicional en el repository
        return progreso;
    }

    // Métodos de validación privados
    private void validarCreacionActividad(CrearActividadDto request) {
        if (request.getCursoId() == null) {
            throw new IllegalArgumentException("El curso es obligatorio");
        }
        
        if (request.getTipo() == null || request.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de actividad es obligatorio");
        }
        
        // Validar que el tipo sea válido
        try {
            TipoActividad.valueOf(request.getTipo().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Tipo de actividad no válido: " + request.getTipo());
        }
        
        // Validar que no exista otra actividad con el mismo título en el mismo curso
        if (actividadRepository.existePorTituloYCurso(request.getTitulo(), request.getCursoId())) {
            throw new IllegalArgumentException("Ya existe una actividad con ese título en el curso");
        }
    }
    
    private void validarDependencias(Long actividadId, List<Long> dependencias) {
        if (actividadRepository.existeDependenciaCiclica(actividadId, dependencias)) {
            throw new IllegalArgumentException("Se detectó una dependencia cíclica en las actividades");
        }
        
        // Validar que todas las dependencias existan
        for (Long depId : dependencias) {
            if (actividadRepository.obtenerPorId(depId).isEmpty()) {
                throw new IllegalArgumentException("Actividad dependiente no encontrada: " + depId);
            }
        }
    }
    
    private Actividad convertirDtoToActividad(CrearActividadDto dto) {
        // Implementar conversión de DTO a domain model
        // Esto podría usar un mapper o hacer la conversión manualmente
        return Actividad.actividadBuilder()
            .titulo(dto.getTitulo())
            .descripcion(dto.getDescripcion())
            .instrucciones(dto.getInstrucciones())
            .tipo(TipoActividad.valueOf(dto.getTipo().toUpperCase()))
            .cursoId(dto.getCursoId())
            .moduloId(dto.getModuloId())
            .orden(dto.getOrden())
            .actividadesDependientes(null) // DTO simplificado no incluye dependencias
            .fechaInicio(dto.getFechaInicio())
            .fechaFin(dto.getFechaFin())
            .build();
    }
    
    private ResultadoActividad convertirDtoToResultado(Long estudianteId, Long actividadId, RegistrarProgresoDto dto) {
        return new ResultadoActividad(
            estudianteId,
            actividadId,
            dto.getNota(),
            ResultadoActividad.EstadoResultado.valueOf(dto.getEstadoFinal().toUpperCase()),
            dto.getNotasPorCriterio(),
            dto.getRetroalimentacionGeneral(),
            dto.getIntentoNumero(),
            dto.getTiempoEmpleadoSegundos()
        );
    }
}
