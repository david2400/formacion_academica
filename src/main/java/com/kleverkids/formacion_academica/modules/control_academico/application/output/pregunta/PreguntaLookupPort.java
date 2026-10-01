package com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.Pregunta;

import java.util.Optional;

/**
 * Puerto de solo lectura, nuevo y aislado, para obtener el modelo de dominio
 * rico {@code Pregunta} a partir de su id.
 *
 * <p>No reutiliza {@link PreguntaRepository#findById(Long)} porque su único
 * adapter ({@code PreguntaJpaAdapter}) tiene ese método implementado como un
 * stub pre-existente ("Implementación mínima por ahora - retornar empty para
 * evitar errores"), ajeno a este trabajo. Arreglar ese stub tocaría un
 * adapter compartido que también implementa {@code PreguntaBancoRepositoryPort},
 * fuera del alcance de Etapa 3 y con riesgo de romper otros consumidores (ver
 * CLAUDE.md #112, modificación mínima). Este puerto se implementa en cambio
 * con el {@code PreguntaJpaRepository} de Spring Data, que sí funciona.</p>
 */
public interface PreguntaLookupPort {

    Optional<Pregunta> obtenerPorId(Long id);
}
