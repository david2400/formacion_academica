package com.kleverkids.formacion_academica.modules.control_academico.domain.exception;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Respuesta de feedback inválida, con el detalle POR PREGUNTA (CLAUDE.md §65:
 * el usuario debe saber qué pregunta falla y por qué, no solo un toast). El
 * GlobalExceptionHandler la expone como 400 con la propiedad {@code errores}
 * (pregunta_id → mensaje).
 */
public class FeedbackValidacionException extends RuntimeException {

    private final Map<Long, String> errores;

    public FeedbackValidacionException(Map<Long, String> errores) {
        super("Revisa las respuestas marcadas antes de enviar.");
        this.errores = Collections.unmodifiableMap(new LinkedHashMap<>(errores));
    }

    public Map<Long, String> getErrores() {
        return errores;
    }
}
