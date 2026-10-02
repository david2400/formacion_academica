package com.kleverkids.formacion_academica.modules.gestion_alumnos.security.jwt;

/** Quién está detrás de un JWT de gestion_alumnos: un Estudiante o un Acudiente. */
public enum TipoSujetoAutenticado {
    ESTUDIANTE,
    ACUDIENTE
}
