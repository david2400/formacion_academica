package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RecursoActividad {
    
    private final String id;
    private final String nombre;
    private final String descripcion;
    private final TipoRecurso tipo;
    private final String url;
    private final Long tamañoBytes;
    private final String formato;
    private final Integer orden;
    private final Boolean obligatorio;
    private final LocalDateTime fechaDisponible;
    private final LocalDateTime fechaExpiracion;
    private final ConfiguracionAcceso configuracionAcceso;
    private final MetadatosRecurso metadatos;
    
    private RecursoActividad(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.tipo = builder.tipo;
        this.url = builder.url;
        this.tamañoBytes = builder.tamañoBytes;
        this.formato = builder.formato;
        this.orden = builder.orden;
        this.obligatorio = builder.obligatorio;
        this.fechaDisponible = builder.fechaDisponible;
        this.fechaExpiracion = builder.fechaExpiracion;
        this.configuracionAcceso = builder.configuracionAcceso;
        this.metadatos = builder.metadatos;
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static RecursoActividad crearArchivo(String nombre, String url, Long tamañoBytes, String formato) {
        return builder()
            .id(java.util.UUID.randomUUID().toString())
            .nombre(nombre)
            .url(url)
            .tamañoBytes(tamañoBytes)
            .formato(formato)
            .tipo(TipoRecurso.ARCHIVO)
            .obligatorio(false)
            .configuracionAcceso(ConfiguracionAcceso.porDefecto())
            .build();
    }
    
    public static RecursoActividad crearVideo(String nombre, String url, Integer duracionSegundos) {
        MetadatosRecurso metadatos = MetadatosRecurso.builder()
            .duracion(duracionSegundos)
            .calidad("HD")
            .build();
            
        return builder()
            .id(java.util.UUID.randomUUID().toString())
            .nombre(nombre)
            .url(url)
            .tipo(TipoRecurso.VIDEO)
            .formato("mp4")
            .obligatorio(false)
            .configuracionAcceso(ConfiguracionAcceso.porDefecto())
            .metadatos(metadatos)
            .build();
    }
    
    public static RecursoActividad crearEnlace(String nombre, String url) {
        return builder()
            .id(java.util.UUID.randomUUID().toString())
            .nombre(nombre)
            .url(url)
            .tipo(TipoRecurso.ENLACE)
            .obligatorio(false)
            .configuracionAcceso(ConfiguracionAcceso.porDefecto())
            .build();
    }
    
    public boolean estaDisibilidad() {
        LocalDateTime ahora = LocalDateTime.now();
        boolean despuesDeInicio = fechaDisponible == null || ahora.isAfter(fechaDisponible);
        boolean antesDeExpiracion = fechaExpiracion == null || ahora.isBefore(fechaExpiracion);
        return despuesDeInicio && antesDeExpiracion;
    }
    
    public boolean requiereDescarga() {
        return tipo == TipoRecurso.ARCHIVO || tipo == TipoRecurso.DOCUMENTO;
    }
    
    public boolean esVisualizableEnLinea() {
        return Set.of(TipoRecurso.VIDEO, TipoRecurso.IMAGEN, TipoRecurso.DOCUMENTO).contains(tipo);
    }
    
    public String getExtension() {
        if (formato != null && formato.contains(".")) {
            return formato.substring(formato.lastIndexOf(".") + 1).toLowerCase();
        }
        return formato != null ? formato.toLowerCase() : "";
    }
    
    public String getTamañoFormateado() {
        if (tamañoBytes == null) return "N/A";
        
        if (tamañoBytes < 1024) return tamañoBytes + " B";
        if (tamañoBytes < 1024 * 1024) return (tamañoBytes / 1024) + " KB";
        if (tamañoBytes < 1024 * 1024 * 1024) return String.format("%.1f MB", tamañoBytes / (1024.0 * 1024));
        return String.format("%.1f GB", tamañoBytes / (1024.0 * 1024 * 1024));
    }
    
    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public TipoRecurso getTipo() { return tipo; }
    public String getUrl() { return url; }
    public Long getTamañoBytes() { return tamañoBytes; }
    public String getFormato() { return formato; }
    public Integer getOrden() { return orden; }
    public Boolean getObligatorio() { return obligatorio; }
    public LocalDateTime getFechaDisponible() { return fechaDisponible; }
    public LocalDateTime getFechaExpiracion() { return fechaExpiracion; }
    public ConfiguracionAcceso getConfiguracionAcceso() { return configuracionAcceso; }
    public MetadatosRecurso getMetadatos() { return metadatos; }
    
    public static class Builder {
        private String id;
        private String nombre;
        private String descripcion;
        private TipoRecurso tipo;
        private String url;
        private Long tamañoBytes;
        private String formato;
        private Integer orden = 0;
        private Boolean obligatorio = false;
        private LocalDateTime fechaDisponible;
        private LocalDateTime fechaExpiracion;
        private ConfiguracionAcceso configuracionAcceso;
        private MetadatosRecurso metadatos;
        
        public Builder id(String id) { this.id = id; return this; }
        public Builder nombre(String nombre) { this.nombre = nombre; return this; }
        public Builder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public Builder tipo(TipoRecurso tipo) { this.tipo = tipo; return this; }
        public Builder url(String url) { this.url = url; return this; }
        public Builder tamañoBytes(Long tamañoBytes) { this.tamañoBytes = tamañoBytes; return this; }
        public Builder formato(String formato) { this.formato = formato; return this; }
        public Builder orden(Integer orden) { this.orden = orden; return this; }
        public Builder obligatorio(Boolean obligatorio) { this.obligatorio = obligatorio; return this; }
        public Builder fechaDisponible(LocalDateTime fechaDisponible) { this.fechaDisponible = fechaDisponible; return this; }
        public Builder fechaExpiracion(LocalDateTime fechaExpiracion) { this.fechaExpiracion = fechaExpiracion; return this; }
        public Builder configuracionAcceso(ConfiguracionAcceso configuracionAcceso) { this.configuracionAcceso = configuracionAcceso; return this; }
        public Builder metadatos(MetadatosRecurso metadatos) { this.metadatos = metadatos; return this; }
        
        public RecursoActividad build() {
            return new RecursoActividad(this);
        }
    }
    
    public enum TipoRecurso {
        ARCHIVO, DOCUMENTO, VIDEO, IMAGEN, AUDIO, ENLACE, INTERACTIVO, FORMULARIO
    }
    
    public static record ConfiguracionAcceso(
        Boolean permiteDescarga,
        Boolean permiteVisualizacionEnLinea,
        Integer maximoVisualizaciones,
        Integer tiempoAccesoMinutos,
        Set<String> formatosPermitidos
    ) {
        public static ConfiguracionAcceso porDefecto() {
            return new ConfiguracionAcceso(
                true, true, null, null, Set.of()
            );
        }
        
        public static ConfiguracionAcceso soloVisualizacion() {
            return new ConfiguracionAcceso(
                false, true, null, null, Set.of()
            );
        }
    }
    
    public static record MetadatosRecurso(
        String autor,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaUltimaModificacion,
        String idioma,
        Integer duracion, // Para videos/audio en segundos
        String calidad, // Para videos: "HD", "4K", etc.
        Integer paginas, // Para documentos
        String resolucion, // Para imágenes: "1920x1080"
        List<String> etiquetas,
        Map<String, Object> metadatosPersonalizados
    ) {
        public static Builder builder() {
            return new Builder();
        }
        
        public static class Builder {
            private String autor;
            private LocalDateTime fechaCreacion = LocalDateTime.now();
            private LocalDateTime fechaUltimaModificacion = LocalDateTime.now();
            private String idioma = "es";
            private Integer duracion;
            private String calidad;
            private Integer paginas;
            private String resolucion;
            private List<String> etiquetas = List.of();
            private Map<String, Object> metadatosPersonalizados = Map.of();
            
            public Builder autor(String autor) { this.autor = autor; return this; }
            public Builder fechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; return this; }
            public Builder fechaUltimaModificacion(LocalDateTime fechaUltimaModificacion) { this.fechaUltimaModificacion = fechaUltimaModificacion; return this; }
            public Builder idioma(String idioma) { this.idioma = idioma; return this; }
            public Builder duracion(Integer duracion) { this.duracion = duracion; return this; }
            public Builder calidad(String calidad) { this.calidad = calidad; return this; }
            public Builder paginas(Integer paginas) { this.paginas = paginas; return this; }
            public Builder resolucion(String resolucion) { this.resolucion = resolucion; return this; }
            public Builder etiquetas(List<String> etiquetas) { this.etiquetas = etiquetas; return this; }
            public Builder metadatosPersonalizados(Map<String, Object> metadatosPersonalizados) { this.metadatosPersonalizados = metadatosPersonalizados; return this; }
            
            public MetadatosRecurso build() {
                return new MetadatosRecurso(
                    autor, fechaCreacion, fechaUltimaModificacion, idioma,
                    duracion, calidad, paginas, resolucion, etiquetas, metadatosPersonalizados
                );
            }
        }
    }
}
