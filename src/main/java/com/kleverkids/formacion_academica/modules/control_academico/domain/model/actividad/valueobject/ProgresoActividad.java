package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ProgresoActividad {
    
    private final Map<Long, ProgresoEstudiante> progresoPorEstudiante;
    private final Long actividadId;
    private final LocalDateTime ultimaActualizacion;
    
    private ProgresoActividad(Builder builder) {
        this.actividadId = builder.actividadId;
        this.progresoPorEstudiante = new ConcurrentHashMap<>(builder.progresoPorEstudiante);
        this.ultimaActualizacion = LocalDateTime.now();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public void registrarInicio(Long estudianteId) {
        progresoPorEstudiante.computeIfAbsent(estudianteId, id -> 
            ProgresoEstudiante.builder()
                .estudianteId(id)
                .estado(EstadoProgreso.NO_INICIADO)
                .build()
        ).iniciar();
    }
    
    public void registrarCompletado(Long estudianteId, ResultadoActividad resultado) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        if (progreso != null) {
            progreso.completar(resultado);
        }
    }
    
    public void registrarAprobacion(Long estudianteId) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        if (progreso != null) {
            progreso.aprobar();
        }
    }
    
    public void actualizarPorcentaje(Long estudianteId, Integer porcentaje) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        if (progreso != null) {
            progreso.actualizarPorcentaje(porcentaje);
        }
    }
    
    public void registrarInteraccion(Long estudianteId, InteraccionActividad interaccion) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        if (progreso != null) {
            progreso.registrarInteraccion(interaccion);
        }
    }
    
    public boolean estaCompletadaPor(Long estudianteId) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        return progreso != null && progreso.estaCompletado();
    }
    
    public boolean estaAprobadaPor(Long estudianteId) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        return progreso != null && progreso.estaAprobado();
    }
    
    public Integer getPorcentajeAvance(Long estudianteId) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        return progreso != null ? progreso.porcentajeAvance() : 0;
    }
    
    public EstadoProgreso getEstado(Long estudianteId) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        return progreso != null ? progreso.estado() : EstadoProgreso.NO_INICIADO;
    }
    
    public Long getTiempoEmpleado(Long estudianteId) {
        ProgresoEstudiante progreso = progresoPorEstudiante.get(estudianteId);
        return progreso != null ? progreso.tiempoTotalEmpleado() : 0L;
    }
    
    public MetricasActividad getMetricasGenerales() {
        int totalEstudiantes = progresoPorEstudiante.size();
        long completados = progresoPorEstudiante.values().stream()
            .mapToLong(p -> p.estaCompletado() ? 1 : 0)
            .sum();
        long aprobados = progresoPorEstudiante.values().stream()
            .mapToLong(p -> p.estaAprobado() ? 1 : 0)
            .sum();
        
        double porcentajeCompletado = totalEstudiantes > 0 ? (double) completados / totalEstudiantes * 100 : 0;
        double porcentajeAprobado = totalEstudiantes > 0 ? (double) aprobados / totalEstudiantes * 100 : 0;
        
        return new MetricasActividad(
            totalEstudiantes,
            (int) completados,
            (int) aprobados,
            porcentajeCompletado,
            porcentajeAprobado
        );
    }
    
    // Getters
    public Long getActividadId() { return actividadId; }
    public LocalDateTime getUltimaActualizacion() { return ultimaActualizacion; }
    public Map<Long, ProgresoEstudiante> getProgresoPorEstudiante() { return new ConcurrentHashMap<>(progresoPorEstudiante); }
    
    public static class Builder {
        private Long actividadId;
        private Map<Long, ProgresoEstudiante> progresoPorEstudiante = new ConcurrentHashMap<>();
        
        public Builder actividadId(Long actividadId) { this.actividadId = actividadId; return this; }
        public Builder progresoPorEstudiante(Map<Long, ProgresoEstudiante> progresoPorEstudiante) { 
            this.progresoPorEstudiante = new ConcurrentHashMap<>(progresoPorEstudiante); 
            return this; 
        }
        
        public ProgresoActividad build() {
            return new ProgresoActividad(this);
        }
    }
    
    public static record ProgresoEstudiante(
        Long estudianteId,
        EstadoProgreso estado,
        Integer porcentajeAvance,
        LocalDateTime fechaInicio,
        LocalDateTime fechaCompletado,
        LocalDateTime fechaUltimaActualizacion,
        Long tiempoTotalEmpleado,
        List<InteraccionActividad> interacciones,
        ResultadoActividad resultadoFinal,
        Integer intentosRealizados
    ) {
        public static Builder builder() {
            return new Builder();
        }
        
        public void iniciar() {
            // Lógica de inicio
        }
        
        public void completar(ResultadoActividad resultado) {
            // Lógica de completado
        }
        
        public void aprobar() {
            // Lógica de aprobación
        }
        
        public void actualizarPorcentaje(Integer porcentaje) {
            // Lógica de actualización
        }
        
        public void registrarInteraccion(InteraccionActividad interaccion) {
            // Lógica de registro de interacción
        }
        
        public boolean estaCompletado() {
            return estado == EstadoProgreso.COMPLETADO || estado == EstadoProgreso.APROBADO;
        }
        
        public boolean estaAprobado() {
            return estado == EstadoProgreso.APROBADO;
        }
        
        public static class Builder {
            private Long estudianteId;
            private EstadoProgreso estado = EstadoProgreso.NO_INICIADO;
            private Integer porcentajeAvance = 0;
            private LocalDateTime fechaInicio;
            private LocalDateTime fechaCompletado;
            private LocalDateTime fechaUltimaActualizacion = LocalDateTime.now();
            private Long tiempoTotalEmpleado = 0L;
            private List<InteraccionActividad> interacciones = List.of();
            private ResultadoActividad resultadoFinal;
            private Integer intentosRealizados = 0;
            
            public Builder estudianteId(Long estudianteId) { this.estudianteId = estudianteId; return this; }
            public Builder estado(EstadoProgreso estado) { this.estado = estado; return this; }
            public Builder porcentajeAvance(Integer porcentajeAvance) { this.porcentajeAvance = porcentajeAvance; return this; }
            public Builder fechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; return this; }
            public Builder fechaCompletado(LocalDateTime fechaCompletado) { this.fechaCompletado = fechaCompletado; return this; }
            public Builder fechaUltimaActualizacion(LocalDateTime fechaUltimaActualizacion) { this.fechaUltimaActualizacion = fechaUltimaActualizacion; return this; }
            public Builder tiempoTotalEmpleado(Long tiempoTotalEmpleado) { this.tiempoTotalEmpleado = tiempoTotalEmpleado; return this; }
            public Builder interacciones(List<InteraccionActividad> interacciones) { this.interacciones = interacciones; return this; }
            public Builder resultadoFinal(ResultadoActividad resultadoFinal) { this.resultadoFinal = resultadoFinal; return this; }
            public Builder intentosRealizados(Integer intentosRealizados) { this.intentosRealizados = intentosRealizados; return this; }
            
            public ProgresoEstudiante build() {
                return new ProgresoEstudiante(
                    estudianteId, estado, porcentajeAvance, fechaInicio, fechaCompletado,
                    fechaUltimaActualizacion, tiempoTotalEmpleado, interacciones,
                    resultadoFinal, intentosRealizados
                );
            }
        }
    }
    
    public enum EstadoProgreso {
        NO_INICIADO, EN_PROGRESO, COMPLETADO, APROBADO, RECHAZADO, EXPIRADO
    }
    
    public static record InteraccionActividad(
        String id,
        Long estudianteId,
        String tipo, // "VISUALIZACION", "CLIC", "RESPUESTA", "NAVEGACION"
        String descripcion,
        LocalDateTime timestamp,
        Map<String, Object> datosAdicionales
    ) {}
    
    public static record MetricasActividad(
        int totalEstudiantes,
        int estudiantesCompletados,
        int estudiantesAprobados,
        double porcentajeCompletado,
        double porcentajeAprobado
    ) {}
}
