package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Registro de handlers poblado automáticamente por Spring: cada
 * {@code @Component("CODIGO_TIPO")} que implemente {@link ActivityHandler} se
 * agrega solo a este mapa por nombre de bean. Agregar un tipo nuevo no toca
 * esta clase (mejora sobre el patrón switch del Factory de preguntas, ver
 * arquitectura aprobada).
 */
@Component
@RequiredArgsConstructor
public class ActivityHandlerRegistry {

    private final Map<String, ActivityHandler> handlers;

    public ActivityHandler obtener(String tipo) {
        ActivityHandler handler = handlers.get(tipo);
        if (handler == null) {
            throw new IllegalStateException(
                    "No existe un manejador registrado para el tipo de actividad '" + tipo + "'");
        }
        return handler;
    }
}
