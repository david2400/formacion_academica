package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.valueobject.CategoriaTipoActividad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Registro extensible de tipos de actividad (reemplaza el enum fijo
 * TipoActividadEntity que tenía la Actividad anterior). Agregar un tipo nuevo
 * es una fila nueva aquí + una implementación de ActivityHandler, sin tocar
 * el núcleo (ver arquitectura aprobada, sección "ActivityTypeDefinition").
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTypeDefinition {

    private Long id;

    /** Código único usado como clave del registro de handlers (ej. "QUESTION"). */
    private String type;

    private String nombre;
    private String descripcion;
    private String icono;
    private CategoriaTipoActividad categoria;

    /** JSON Schema de la configuración específica que este tipo necesita. */
    private Map<String, Object> configuracionSchema;

    /** Flags livianos: soportaIntentos, soportaPuntaje, esExterno, etc. */
    private Map<String, Object> capacidades;

    private boolean activo;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
