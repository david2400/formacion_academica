package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.valueobject.CategoriaTipoActividad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearActivityTypeDefinitionDto {

    @NotBlank(message = "El código del tipo de actividad es obligatorio")
    private String type;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    private String descripcion;
    private String icono;

    @NotNull(message = "La categoría es obligatoria")
    private CategoriaTipoActividad categoria;

    private Map<String, Object> configuracionSchema;
    private Map<String, Object> capacidades;

    /** Si no se envía, el tipo se crea activo. */
    private Boolean activo;
}
