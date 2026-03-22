package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import java.util.List;

public class RbricaEmbeddable {
    
    private List<CriterioEmbeddable> criterios;
    
    public RbricaEmbeddable() {}
    
    public RbricaEmbeddable(List<CriterioEmbeddable> criterios) {
        this.criterios = criterios;
    }
    
    public List<CriterioEmbeddable> getCriterios() { return criterios; }
    public void setCriterios(List<CriterioEmbeddable> criterios) { this.criterios = criterios; }
    
    public static class CriterioEmbeddable {
        private String nombre;
        private String descripcion;
        private int puntajeMaximo;
        private List<NivelEmbeddable> niveles;
        
        public CriterioEmbeddable() {}
        
        public CriterioEmbeddable(String nombre, String descripcion, int puntajeMaximo, List<NivelEmbeddable> niveles) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.puntajeMaximo = puntajeMaximo;
            this.niveles = niveles;
        }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        
        public int getPuntajeMaximo() { return puntajeMaximo; }
        public void setPuntajeMaximo(int puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; }
        
        public List<NivelEmbeddable> getNiveles() { return niveles; }
        public void setNiveles(List<NivelEmbeddable> niveles) { this.niveles = niveles; }
    }
    
    public static class NivelEmbeddable {
        private String nombre;
        private String descripcion;
        private int puntaje;
        
        public NivelEmbeddable() {}
        
        public NivelEmbeddable(String nombre, String descripcion, int puntaje) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.puntaje = puntaje;
        }
        
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        
        public int getPuntaje() { return puntaje; }
        public void setPuntaje(int puntaje) { this.puntaje = puntaje; }
    }
}
