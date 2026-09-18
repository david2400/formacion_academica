package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.output.cuenta;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.UsuarioAcceso;

import java.util.Optional;

/**
 * Acceso a la identidad centralizada de personas (User + Client) y a su
 * asignación de empresa, gestionadas por access_control.
 *
 * <p>Los módulos de negocio dependen de esta interfaz, no de cómo se llega a
 * access_control. Hoy la implementación es SQL directo contra el esquema
 * {@code security} (mismo patrón que {@code MotorEstadosPort} para el motor de
 * estados); podría cambiar a un cliente HTTP el día que ambas bases dejen de
 * vivir en el mismo servidor MySQL, sin que esto —ni Acudiente/Estudiante— se
 * enteren.
 *
 * <p>Se referencia por {@code usuarioId} porque el User ya trae embebida la
 * identidad de su Client, y porque la asignación a empresa vive en la relación
 * usuario-empresa de access_control (UserCompany), no en el Client.
 *
 * <p><b>access_control es la fuente de verdad de la identidad.</b> Los campos
 * de nombre/documento replicados en Acudiente y Estudiante existen para poder
 * listar y filtrar sin salir a la red; cuando discrepan, manda access_control.
 */
public interface CuentaUsuarioPort {

    /**
     * Datos básicos del usuario y de su cliente asociado.
     *
     * <p>Vacío si el usuarioId no existe en access_control.
     */
    Optional<UsuarioAcceso> obtenerUsuario(Long usuarioId);

    /**
     * Verifica que el usuario esté asignado (vigente) a la empresa indicada en
     * access_control, antes de vincularlo a un Acudiente o Estudiante.
     */
    boolean usuarioPerteneceAEmpresa(Long usuarioId, Long empresaId);
}
