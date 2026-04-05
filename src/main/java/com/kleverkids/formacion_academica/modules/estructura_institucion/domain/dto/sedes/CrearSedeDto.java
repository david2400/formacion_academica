package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearSedeDto {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;
    
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no puede exceder 50 caracteres")
    private String ciudadId;
    
    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 50, message = "El departamento no puede exceder 50 caracteres")
    private String departamentoId;
    
    @NotBlank(message = "El país es obligatorio")
    @Size(max = 50, message = "El país no puede exceder 50 caracteres")
    private String pais;
    
    @Pattern(regexp = "^[+]?[0-9\\s\\-\\(\\)]+$", message = "El teléfono tiene un formato inválido")
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;
    
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @NotBlank(message = "El contacto principal es obligatorio")
    @Size(max = 100, message = "El contacto principal no puede exceder 100 caracteres")
    private String contactoPrincipal;
    
    @Pattern(regexp = "^[+]?[0-9\\s\\-\\(\\)]+$", message = "El teléfono del contacto tiene un formato inválido")
    @Size(max = 20, message = "El teléfono del contacto no puede exceder 20 caracteres")
    private String telefonoContacto;
    
    @Email(message = "El email del contacto debe ser válido")
    @Size(max = 100, message = "El email del contacto no puede exceder 100 caracteres")
    private String emailContacto;

    private Boolean activo;
}
