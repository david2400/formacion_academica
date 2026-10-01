package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.valueobject.CategoriaTipoActividad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * No permite cambiar {@code type} (el código es la clave del registro de
 * handlers; cambiarlo rompería la actividad ya configurada con ese tipo).
 */
@Data
@NoArgsConstructor
public class ActualizarActivityTypeDefinitionDto {

    @NotNull
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;
    private String icono;
    private CategoriaTipoActividad categoria;
    private Map<String, Object> configuracionSchema;
    private Map<String, Object> capacidades;
    private Boolean activo;
}
