package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad;

import java.time.LocalDateTime;

public record ActividadSimple(
    Long id,
    String titulo,
    String descripcion,
    String instrucciones,
    String tipo,
    String estado,
    Long cursoId,
    Long moduloId,
    Integer orden,
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin
) {
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String titulo;
        private String descripcion;
        private String instrucciones;
        private String tipo = "LECTURA";
        private String estado = "BORRADOR";
        private Long cursoId;
        private Long moduloId;
        private Integer orden;
        private LocalDateTime fechaInicio;
        private LocalDateTime fechaFin;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder titulo(String titulo) { this.titulo = titulo; return this; }
        public Builder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public Builder instrucciones(String instrucciones) { this.instrucciones = instrucciones; return this; }
        public Builder tipo(String tipo) { this.tipo = tipo; return this; }
        public Builder estado(String estado) { this.estado = estado; return this; }
        public Builder cursoId(Long cursoId) { this.cursoId = cursoId; return this; }
        public Builder moduloId(Long moduloId) { this.moduloId = moduloId; return this; }
        public Builder orden(Integer orden) { this.orden = orden; return this; }
        public Builder fechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; return this; }
        public Builder fechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; return this; }

        public ActividadSimple build() {
            return new ActividadSimple(id, titulo, descripcion, instrucciones, tipo, estado, cursoId, moduloId, orden, fechaInicio, fechaFin);
        }
    }
}
