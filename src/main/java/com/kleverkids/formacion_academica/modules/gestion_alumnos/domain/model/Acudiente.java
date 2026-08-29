package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Acudiente {
    private Long id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String parentesco;
    private String telefono;
    private String correo;
    private boolean esPrincipal;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private Instant createdAt;
    private Instant updatedAt;
}
