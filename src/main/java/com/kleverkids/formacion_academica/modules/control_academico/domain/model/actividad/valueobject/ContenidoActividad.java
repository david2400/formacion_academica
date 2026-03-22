package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.util.Map;
import java.util.List;

public class ContenidoActividad {
    
    private final String tipo;
    private final Map<String, Object> datos;
    private final List<RecursoContenido> recursos;
    private final MetadatosContenido metadatos;
    
    private ContenidoActividad(Builder builder) {
        this.tipo = builder.tipo;
        this.datos = builder.datos;
        this.recursos = builder.recursos;
        this.metadatos = builder.metadatos;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ContenidoActividad crearLectura(String texto, List<RecursoContenido> recursos) {
        return builder()
            .tipo("LECTURA")
            .datos(Map.of("texto", texto))
            .recursos(recursos)
            .metadatos(MetadatosContenido.builder()
                .formato("text/html")
                .duracionEstimada(15)
                .build())
            .build();
    }
    
    public static ContenidoActividad crearVideo(String url, Integer duracionSegundos) {
        return builder()
            .tipo("VIDEO")
            .datos(Map.of(
                "url", url,
                "duracion", duracionSegundos,
                "autoplay", false
            ))
            .metadatos(MetadatosContenido.builder()
                .formato("video/mp4")
                .duracionEstimada(duracionSegundos / 60)
                .build())
            .build();
    }
    
    public static ContenidoActividad crearCuestionario(List<PreguntaContenido> preguntas) {
        return builder()
            .tipo("CUESTIONARIO")
            .datos(Map.of(
                "preguntas", preguntas,
                "aleatorio", false,
                "mostrarResultados", true
            ))
            .metadatos(MetadatosContenido.builder()
                .formato("application/json")
                .duracionEstimada(preguntas.size() * 2)
                .build())
            .build();
    }
    
    public String getTipo() { return tipo; }
    public Map<String, Object> getDatos() { return datos; }
    public List<RecursoContenido> getRecursos() { return recursos; }
    public MetadatosContenido getMetadatos() { return metadatos; }
    
    public static class Builder {
        private String tipo;
        private Map<String, Object> datos;
        private List<RecursoContenido> recursos;
        private MetadatosContenido metadatos;
        
        public Builder tipo(String tipo) { this.tipo = tipo; return this; }
        public Builder datos(Map<String, Object> datos) { this.datos = datos; return this; }
        public Builder recursos(List<RecursoContenido> recursos) { this.recursos = recursos; return this; }
        public Builder metadatos(MetadatosContenido metadatos) { this.metadatos = metadatos; return this; }
        
        public ContenidoActividad build() {
            return new ContenidoActividad(this);
        }
    }
    
    public static record RecursoContenido(
        String id,
        String nombre,
        String tipo,
        String url,
        Long tamañoBytes,
        String formato
    ) {}
    
    public static record PreguntaContenido(
        String id,
        String texto,
        String tipo,
        List<OpcionPregunta> opciones,
        Boolean requerida,
        Integer puntos
    ) {}
    
    public static record OpcionPregunta(
        String id,
        String texto,
        Boolean esCorrecta,
        String retroalimentacion
    ) {}
    
    public static record MetadatosContenido(
        String formato,
        Integer duracionEstimada,
        String idioma,
        String nivelDificultad,
        List<String> etiquetas
    ) {
        public static Builder builder() {
            return new Builder();
        }
        
        public static class Builder {
            private String formato;
            private Integer duracionEstimada;
            private String idioma = "es";
            private String nivelDificultad = "medio";
            private List<String> etiquetas = List.of();
            
            public Builder formato(String formato) { this.formato = formato; return this; }
            public Builder duracionEstimada(Integer duracionEstimada) { this.duracionEstimada = duracionEstimada; return this; }
            public Builder idioma(String idioma) { this.idioma = idioma; return this; }
            public Builder nivelDificultad(String nivelDificultad) { this.nivelDificultad = nivelDificultad; return this; }
            public Builder etiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; return this; }
            
            public MetadatosContenido build() {
                return new MetadatosContenido(formato, duracionEstimada, idioma, nivelDificultad, etiquetas);
            }
        }
    }
}
