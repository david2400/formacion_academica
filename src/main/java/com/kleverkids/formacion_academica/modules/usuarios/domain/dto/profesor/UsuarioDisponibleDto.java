package com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Usuario de la empresa que todavía no es profesor (candidato en el formulario de alta). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDisponibleDto {
    private Long usuarioId;
    private String usuario;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String estadoUsuario;
}
