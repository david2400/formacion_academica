package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject.EvaluacionConfiguracion.ResultadoActividad;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Actividad extends AuditInfo {
    
    private final Long id;
    private final String titulo;
    private final String descripcion;
    private final String instrucciones;
    private final TipoActividad tipo;
    private final ContenidoActividad contenido;
    private final ConfiguracionActividad configuracion;
    private final EstadoActividad estado;
    private final Long cursoId;
    private final Long moduloId;
    private final Integer orden;
    private final List<Long> actividadesDependientes;
    private final List<RecursoActividad> recursos;
    private final EvaluacionConfiguracion evaluacion;
    private final LocalDateTime fechaInicio;
    private final LocalDateTime fechaFin;
    private final ProgresoActividad progreso;
    
    private Actividad(Builder builder) {
        this.id = builder.id;
        this.titulo = builder.titulo;
        this.descripcion = builder.descripcion;
        this.instrucciones = builder.instrucciones;
        this.tipo = builder.tipo;
        this.contenido = builder.contenido;
        this.configuracion = builder.configuracion;
        this.estado = builder.estado;
        this.cursoId = builder.cursoId;
        this.moduloId = builder.moduloId;
        this.orden = builder.orden;
        this.actividadesDependientes = builder.actividadesDependientes;
        this.recursos = builder.recursos;
        this.evaluacion = builder.evaluacion;
        this.fechaInicio = builder.fechaInicio;
        this.fechaFin = builder.fechaFin;
        this.progreso = builder.progreso;
    }
    
    public static Builder actividadBuilder() {
        return new Builder();
    }
    
    // Métodos de comportamiento de dominio - Comentados porque los records son inmutables
    /*
    public void iniciar() {
        validarPuedeIniciar();
        this.estado = EstadoActividad.ACTIVA;
    }
    
    public void completar(Long estudianteId, ResultadoActividad resultado) {
        validarPuedeCompletar(estudianteId);
        this.progreso.registrarCompletado(estudianteId, resultado);
        evaluarAprobacion(resultado);
    }
    
    public void bloquear() {
        this.estado = EstadoActividad.BLOQUEADA;
    }
    
    public void desbloquear() {
        this.estado = EstadoActividad.ACTIVA;
    }
    */
    
    public boolean estaDisponiblePara(Long estudianteId) {
        return estado == EstadoActividad.ACTIVA 
            && estaEnPeriodoValido()
            && cumpleDependencias(estudianteId);
    }
    
    private void validarPuedeIniciar() {
        if (estado != EstadoActividad.BORRADOR) {
            throw new IllegalStateException("Solo se puede iniciar una actividad en estado BORRADOR");
        }
        if (!estaEnPeriodoValido()) {
            throw new IllegalStateException("La actividad no está en período válido");
        }
    }
    
    private void validarPuedeCompletar(Long estudianteId) {
        if (!estaDisponiblePara(estudianteId)) {
            throw new IllegalStateException("La actividad no está disponible para el estudiante");
        }
    }
    
    private boolean estaEnPeriodoValido() {
        LocalDateTime ahora = LocalDateTime.now();
        return (fechaInicio == null || ahora.isAfter(fechaInicio)) 
            && (fechaFin == null || ahora.isBefore(fechaFin));
    }
    
    private boolean cumpleDependencias(Long estudianteId) {
        return actividadesDependientes.stream()
            .allMatch(depId -> progreso.estaCompletadaPor(depId));
    }
    
    private void evaluarAprobacion(EvaluacionConfiguracion.ResultadoActividad resultado) {
        if (evaluacion != null && evaluacion.esAutomatica()) {
            boolean aprobado = evaluacion.evaluar(resultado);
            if (aprobado) {
                // progreso.registrarAprobacion(estudianteId); // Comentado - estudianteId no disponible aquí
            }
        }
    }
    
    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getInstrucciones() { return instrucciones; }
    public TipoActividad getTipo() { return tipo; }
    public ContenidoActividad getContenido() { return contenido; }
    public ConfiguracionActividad getConfiguracion() { return configuracion; }
    public EstadoActividad getEstado() { return estado; }
    public Long getCursoId() { return cursoId; }
    public Long getModuloId() { return moduloId; }
    public Integer getOrden() { return orden; }
    public List<Long> getActividadesDependientes() { return actividadesDependientes; }
    public List<RecursoActividad> getRecursos() { return recursos; }
    public EvaluacionConfiguracion getEvaluacion() { return evaluacion; }
    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public LocalDateTime getFechaFin() { return fechaFin; }
    public ProgresoActividad getProgreso() { return progreso; }
    
    public static class Builder {
        private Long id;
        private String titulo;
        private String descripcion;
        private String instrucciones;
        private TipoActividad tipo;
        private ContenidoActividad contenido;
        private ConfiguracionActividad configuracion;
        private EstadoActividad estado = EstadoActividad.BORRADOR;
        private Long cursoId;
        private Long moduloId;
        private Integer orden;
        private List<Long> actividadesDependientes = List.of();
        private List<RecursoActividad> recursos = List.of();
        private EvaluacionConfiguracion evaluacion;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaFin;
        private ProgresoActividad progreso;
        
        public Builder id(Long id) { this.id = id; return this; }
        public Builder titulo(String titulo) { this.titulo = titulo; return this; }
        public Builder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public Builder instrucciones(String instrucciones) { this.instrucciones = instrucciones; return this; }
        public Builder tipo(TipoActividad tipo) { this.tipo = tipo; return this; }
        public Builder contenido(ContenidoActividad contenido) { this.contenido = contenido; return this; }
        public Builder configuracion(ConfiguracionActividad configuracion) { this.configuracion = configuracion; return this; }
        public Builder estado(EstadoActividad estado) { this.estado = estado; return this; }
        public Builder cursoId(Long cursoId) { this.cursoId = cursoId; return this; }
        public Builder moduloId(Long moduloId) { this.moduloId = moduloId; return this; }
        public Builder orden(Integer orden) { this.orden = orden; return this; }
        public Builder actividadesDependientes(List<Long> actividadesDependientes) { 
            this.actividadesDependientes = actividadesDependientes; 
            return this; 
        }
        public Builder recursos(List<RecursoActividad> recursos) { this.recursos = recursos; return this; }
        public Builder evaluacion(EvaluacionConfiguracion evaluacion) { this.evaluacion = evaluacion; return this; }
        public Builder fechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; return this; }
        public Builder fechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; return this; }
        public Builder progreso(ProgresoActividad progreso) { this.progreso = progreso; return this; }
        
        public Actividad build() {
            return new Actividad(this);
        }
    }
}
