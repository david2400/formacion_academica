package com.kleverkids.formacion_academica.security.accesscontrol;

import java.text.Normalizer;
import java.util.Locale;

/**
 * access_control pone en el claim {@code roles} el NOMBRE del rol (p. ej.
 * "Coordinador Académico"), no un código. Para comparar con la configuración
 * ({@code feedback.seguridad.roles-*}) ambos lados se llevan a la misma forma:
 * sin tildes, en mayúsculas y con "_" en lugar de espacios o guiones
 * ("Coordinador Académico" → "COORDINADOR_ACADEMICO").
 */
public final class RolNormalizado {

    private RolNormalizado() {
    }

    public static String de(String rol) {
        if (rol == null) {
            return "";
        }
        String sinTildes = Normalizer.normalize(rol.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return sinTildes.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]+", "_").replaceAll("^_+|_+$", "");
    }

    /** Autoridad de Spring Security para un rol de access_control. */
    public static String autoridad(String rol) {
        return "ROLE_" + de(rol);
    }
}
