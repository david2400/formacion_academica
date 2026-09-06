package com.kleverkids.formacion_academica.modules.estados;

import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http.MotorEstadosProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

/**
 * Módulo de estados.
 *
 * <p>A diferencia del resto, no tiene entidades ni repositorios: los estados no se
 * guardan aquí. Es un cliente del motor de access_control, que es la única fuente de
 * verdad del ciclo de vida de las entidades.
 *
 * <p>Las tablas de negocio conservan su columna {@code estado_id} como réplica, para
 * poder listar y filtrar sin salir a la red. Se puebla siempre con lo que devuelve
 * el motor, nunca con un valor decidido aquí.
 */
@Configuration
@EnableConfigurationProperties(MotorEstadosProperties.class)
@ComponentScan(basePackages = "com.kleverkids.formacion_academica.modules.estados")
public class EstadosModuleConfig {

    /**
     * Cliente dedicado al motor, con nombre propio para no competir con otros
     * {@code RestClient} que puedan aparecer después.
     *
     * <p>Los timeouts son cortos a propósito: estas llamadas ocurren dentro de
     * transacciones de escritura y una espera larga mantiene bloqueos abiertos en
     * la base de datos.
     */
    @Bean
    public RestClient motorEstadosRestClient(MotorEstadosProperties propiedades) {
        SimpleClientHttpRequestFactory fabrica = new SimpleClientHttpRequestFactory();
        fabrica.setConnectTimeout(propiedades.getConnectTimeout());
        fabrica.setReadTimeout(propiedades.getReadTimeout());

        return RestClient.builder()
                .baseUrl(propiedades.getBaseUrl())
                .requestFactory(fabrica)
                .build();
    }
}
