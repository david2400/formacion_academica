package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sede {
    private Long id;
//        String codigo,
    private String nombre;
    private String descripcion;
    private String direccion;
    private String ciudadId;
    private String departamentoId;
    private String pais;
    private String telefono;
    private String email;
    private String contactoPrincipal;
    private String telefonoContacto;
    private String emailContacto;
    
    private Boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
