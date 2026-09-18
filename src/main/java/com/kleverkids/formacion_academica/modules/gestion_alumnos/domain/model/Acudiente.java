package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    /**
     * Hash de la contraseña propia del acudiente (login con documento +
     * contraseña, sin depender de un User en access_control). Nunca se
     * expone en las respuestas de la API — ver {@link JsonIgnore}.
     */
    @JsonIgnore
    private String password;

    /**
     * Referencia al User centralizado en access_control que representa a este
     * acudiente (el User ya trae embebida la identidad de su Client). Fuente
     * de verdad de la identidad; los campos de arriba (nombres, documento,
     * teléfono, correo) son una copia local para poder listar sin cruzar
     * esquemas.
     */
    private Long usuarioId;

    /** Empresa (tenant) a la que pertenece este acudiente, validada contra
     *  access_control al crear/actualizar. */
    private Long empresaId;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private Instant createdAt;
    private Instant updatedAt;
}
