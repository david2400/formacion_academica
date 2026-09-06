package com.kleverkids.formacion_academica.modules.estados;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.jdbc.MotorEstadosProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Módulo de estados.
 *
 * <p>A diferencia del resto, no tiene entidades ni repositorios propios: los estados
 * no se guardan aquí. Vive en el esquema {@code security}, del motor de máquinas de
 * estados de access_control, y este módulo lo lee y lo escribe por SQL directo.
 *
 * <p>No hace falta un segundo {@code DataSource}: ambos esquemas están en el mismo
 * servidor MySQL, así que la conexión existente llega a los dos con solo prefijar el
 * nombre. Eso además mete los dos cambios en la <b>misma transacción</b>.
 *
 * <p>Las tablas de negocio conservan su columna {@code estado_id} como réplica, para
 * poder listar y filtrar sin cruzar esquemas en cada consulta.
 */
@Configuration
@EnableConfigurationProperties(MotorEstadosProperties.class)
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.estados")
public class EstadosModuleConfig {
}
