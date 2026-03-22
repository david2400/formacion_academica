package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class ConfiguracionActividad {
    
    private final Boolean obligatoria;
    private final Integer intentosPermitidos;
    private final Duration tiempoLimite;
    private final Boolean mostrarRetroalimentacion;
    private final Boolean permiteRevisar;
    private final List<ReglaAcceso> reglasAcceso;
    private final ConfiguracionNotificacion notificaciones;
    private final PoliticaSeguimiento politicaSeguimiento;
    
    private ConfiguracionActividad(Builder builder) {
        this.obligatoria = builder.obligatoria;
        this.intentosPermitidos = builder.intentosPermitidos;
        this.tiempoLimite = builder.tiempoLimite;
        this.mostrarRetroalimentacion = builder.mostrarRetroalimentacion;
        this.permiteRevisar = builder.permiteRevisar;
        this.reglasAcceso = builder.reglasAcceso;
        this.notificaciones = builder.notificaciones;
        this.politicaSeguimiento = builder.politicaSeguimiento;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ConfiguracionActividad porDefecto() {
        return builder()
            .obligatoria(true)
            .intentosPermitidos(3)
            .mostrarRetroalimentacion(true)
            .permiteRevisar(true)
            .reglasAcceso(List.of())
            .notificaciones(ConfiguracionNotificacion.porDefecto())
            .politicaSeguimiento(PoliticaSeguimiento.estandar())
            .build();
    }
    
    public Boolean getObligatoria() { return obligatoria; }
    public Integer getIntentosPermitidos() { return intentosPermitidos; }
    public Duration getTiempoLimite() { return tiempoLimite; }
    public Boolean getMostrarRetroalimentacion() { return mostrarRetroalimentacion; }
    public Boolean getPermiteRevisar() { return permiteRevisar; }
    public List<ReglaAcceso> getReglasAcceso() { return reglasAcceso; }
    public ConfiguracionNotificacion getNotificaciones() { return notificaciones; }
    public PoliticaSeguimiento getPoliticaSeguimiento() { return politicaSeguimiento; }
    
    public boolean permiteAcceso(Long estudianteId, LocalDateTime fechaActual) {
        return reglasAcceso.stream()
            .allMatch(regla -> regla.permiteAcceso(estudianteId, fechaActual));
    }
    
    public boolean tieneTiempoLimite() {
        return tiempoLimite != null && !tiempoLimite.isZero();
    }
    
    public static class Builder {
        private Boolean obligatoria = true;
        private Integer intentosPermitidos = 1;
        private Duration tiempoLimite;
        private Boolean mostrarRetroalimentacion = true;
        private Boolean permiteRevisar = false;
        private List<ReglaAcceso> reglasAcceso = List.of();
        private ConfiguracionNotificacion notificaciones;
        private PoliticaSeguimiento politicaSeguimiento;
        
        public Builder obligatoria(Boolean obligatoria) { this.obligatoria = obligatoria; return this; }
        public Builder intentosPermitidos(Integer intentosPermitidos) { this.intentosPermitidos = intentosPermitidos; return this; }
        public Builder tiempoLimite(Duration tiempoLimite) { this.tiempoLimite = tiempoLimite; return this; }
        public Builder mostrarRetroalimentacion(Boolean mostrarRetroalimentacion) { this.mostrarRetroalimentacion = mostrarRetroalimentacion; return this; }
        public Builder permiteRevisar(Boolean permiteRevisar) { this.permiteRevisar = permiteRevisar; return this; }
        public Builder reglasAcceso(List<ReglaAcceso> reglasAcceso) { this.reglasAcceso = reglasAcceso; return this; }
        public Builder notificaciones(ConfiguracionNotificacion notificaciones) { this.notificaciones = notificaciones; return this; }
        public Builder politicaSeguimiento(PoliticaSeguimiento politicaSeguimiento) { this.politicaSeguimiento = politicaSeguimiento; return this; }
        
        public ConfiguracionActividad build() {
            return new ConfiguracionActividad(this);
        }
    }
    
    public static record ReglaAcceso(
        String tipo, // "FECHA", "PROGRESO", "GRUPO", "PERFIL"
        String condicion, // "DESPUES_DE", "ANTES_DE", "PORCENTAJE_MAYOR", "IGUAL_A"
        Object valor,
        Boolean requerida
    ) {
        public boolean permiteAcceso(Long estudianteId, LocalDateTime fechaActual) {
            return switch (tipo) {
                case "FECHA" -> evaluarCondicionFecha(fechaActual);
                case "PROGRESO" -> evaluarCondicionProgreso(estudianteId);
                case "GRUPO" -> evaluarCondicionGrupo(estudianteId);
                case "PERFIL" -> evaluarCondicionPerfil(estudianteId);
                default -> true;
            };
        }
        
        private boolean evaluarCondicionFecha(LocalDateTime fechaActual) {
            if (valor instanceof LocalDateTime fechaValor) {
                return switch (condicion) {
                    case "DESPUES_DE" -> fechaActual.isAfter(fechaValor);
                    case "ANTES_DE" -> fechaActual.isBefore(fechaValor);
                    default -> true;
                };
            }
            return true;
        }
        
        private boolean evaluarCondicionProgreso(Long estudianteId) {
            // Implementar lógica de validación de progreso
            return true;
        }
        
        private boolean evaluarCondicionGrupo(Long estudianteId) {
            // Implementar lógica de validación de grupo
            return true;
        }
        
        private boolean evaluarCondicionPerfil(Long estudianteId) {
            // Implementar lógica de validación de perfil
            return true;
        }
    }
    
    public static record ConfiguracionNotificacion(
        Boolean notificarInicio,
        Boolean notificarCompletado,
        Boolean notificarVencimiento,
        Integer recordatorioHorasAntes,
        Set<String> destinatarios // "ESTUDIANTE", "DOCENTE", "TUTOR"
    ) {
        public static ConfiguracionNotificacion porDefecto() {
            return new ConfiguracionNotificacion(
                true, true, true, 24, Set.of("ESTUDIANTE")
            );
        }
    }
    
    public static record PoliticaSeguimiento(
        Boolean registrarTiempo,
        Boolean registrarInteracciones,
        Boolean registrarProgreso,
        Integer frecuenciaGuardadoSegundos
    ) {
        public static PoliticaSeguimiento estandar() {
            return new PoliticaSeguimiento(
                true, true, true, 30
            );
        }
        
        public static PoliticaSeguimiento minima() {
            return new PoliticaSeguimiento(
                false, false, true, 300
            );
        }
    }
}
