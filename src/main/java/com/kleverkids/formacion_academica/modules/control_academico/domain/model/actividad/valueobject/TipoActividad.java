package com.kleverkids.formacion_academica.modules.control_academico.domain.model.actividad.valueobject;

import java.util.Set;

public enum TipoActividad {
    
    LECTURA("Lectura", "actividades.lectura", Set.of("texto", "pdf", "url")),
    VIDEO("Video", "actividades.video", Set.of("mp4", "youtube", "vimeo")),
    CUESTIONARIO("Cuestionario", "actividades.cuestionario", Set.of("preguntas", "opciones")),
    TAREA_ENTREGABLE("Tarea Entregable", "actividades.tarea", Set.of("archivo", "texto")),
    ACTIVIDAD_INTERACTIVA("Actividad Interactiva", "actividades.interactiva", Set.of("simulacion", "drag-drop")),
    FORO("Foro Discusión", "actividades.foro", Set.of("mensaje", "respuesta")),
    PROYECTO("Proyecto", "actividades.proyecto", Set.of("entregables", "hitos")),
    EXAMEN("Examen", "actividades.examen", Set.of("preguntas", "tiempo", "calificacion"));
    
    private final String nombre;
    private final String codigo;
    private final Set<String> formatosSoportados;
    
    TipoActividad(String nombre, String codigo, Set<String> formatosSoportados) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.formatosSoportados = formatosSoportados;
    }
    
    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public Set<String> getFormatosSoportados() { return formatosSoportados; }
    
    public boolean soportaFormato(String formato) {
        return formatosSoportados.contains(formato.toLowerCase());
    }
    
    public boolean requiereEvaluacion() {
        return this == CUESTIONARIO || this == TAREA_ENTREGABLE || this == EXAMEN || this == PROYECTO;
    }
    
    public boolean esInteractiva() {
        return this == ACTIVIDAD_INTERACTIVA || this == FORO;
    }
    
    public boolean permiteEntrega() {
        return this == TAREA_ENTREGABLE || this == PROYECTO;
    }
}
