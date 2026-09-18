package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Identidad de una persona (acudiente o estudiante) tal como la gestiona de
 * forma centralizada access_control: un {@code User} (usuario/login) junto con
 * los datos básicos de su {@code Client} asociado.
 *
 * <p>Se referencia por {@code usuarioId} (no por el id del Client directamente)
 * porque el usuario ya trae embebida la identidad del cliente, y porque la
 * asignación a empresa vive en la relación usuario-empresa de access_control
 * (tabla UserCompany), no en el Client.
 *
 * <p>Es un modelo de solo lectura para el módulo gestion_alumnos: no se
 * persiste aquí, se consulta a través de
 * {@link com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta.CuentaUsuarioPort}.
 * Acudiente y Estudiante guardan una copia local de estos datos (mismo patrón
 * que {@code estado_id} en el módulo estados) más una referencia a
 * {@code usuarioId}; access_control sigue siendo la fuente de verdad.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioAcceso {
    private Long usuarioId;
    private String usuario;
    private String estadoUsuario;
    private Long clienteId;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String sexo;
    private String genero;
    private String estadoCliente;
}
