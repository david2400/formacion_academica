package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.salon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearSalonDto {
    
    @NotBlank(message = "El código es obligatorio")
    private String codigo;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "La capacidad máxima es obligatoria")
    @Positive(message = "La capacidad máxima debe ser positiva")
    private Integer capacidadMaxima;
    
    private Integer numeroPiso;
    
    private Boolean proyector;
    
    private Boolean pizarronBlanco;
    
    private Boolean aireAcondicionado;
    
    private Long sedeId;

    private Boolean activo;

}
