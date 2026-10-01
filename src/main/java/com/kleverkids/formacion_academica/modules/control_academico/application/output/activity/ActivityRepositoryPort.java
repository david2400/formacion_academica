package com.kleverkids.formacion_academica.modules.control_academico.application.output.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ActualizarActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.AgregarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.CrearActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.FiltroActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ReordenarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;

import java.util.List;
import java.util.Optional;

public interface ActivityRepositoryPort {

    Activity guardar(CrearActivityDto dto);

    Optional<Activity> obtenerPorId(Long id);

    List<Activity> listarTodas();

    List<Activity> buscar(FiltroActivityDto filtro);

    Activity actualizar(ActualizarActivityDto dto);

    void eliminar(Long id);

    /** Congela una ActivityVersion nueva y marca la actividad como PUBLISHED. */
    Activity publicar(Long id);

    Activity archivar(Long id);

    /** Copia la actividad (DRAFT) y su contenido actual; no copia versiones. */
    Activity duplicar(Long id);

    ActivityContentItem agregarContenido(Long activityId, AgregarContenidoActivityDto dto);

    List<ActivityContentItem> listarContenido(Long activityId);

    void eliminarContenido(Long activityId, Long contentItemId);

    List<ActivityContentItem> reordenar(Long activityId, ReordenarContenidoActivityDto dto);

    /** Usado por ActivityAttemptService para validar y calificar una respuesta sin que el módulo de intentos reimplemente el acceso a datos de Activity. */
    Optional<ActivityContentItem> obtenerContenidoPorId(Long contentItemId);
}
