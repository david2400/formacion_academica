package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ActualizarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.AgregarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ArchivarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ConsultarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.CrearActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.DuplicarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.EliminarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.EliminarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ListarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ListarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.PublicarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ReordenarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler.ActivityHandlerRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ActualizarActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.AgregarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.CrearActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.FiltroActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ReordenarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ActivityService implements CrearActivityUseCase,
        ActualizarActivityUseCase,
        ConsultarActivityUseCase,
        ListarActivityUseCase,
        EliminarActivityUseCase,
        PublicarActivityUseCase,
        ArchivarActivityUseCase,
        DuplicarActivityUseCase,
        AgregarContenidoActivityUseCase,
        ListarContenidoActivityUseCase,
        EliminarContenidoActivityUseCase,
        ReordenarContenidoActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;
    private final ActivityHandlerRegistry activityHandlerRegistry;
    private final ActivityEventPublisher activityEventPublisher;

    @Override
    public Activity crear(CrearActivityDto request) {
        activityTypeDefinitionRepositoryPort.obtenerPorId(request.getActivityTypeId())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de actividad indicado no existe"));
        return activityRepositoryPort.guardar(request);
    }

    @Override
    public Activity actualizar(ActualizarActivityDto request) {
        return activityRepositoryPort.actualizar(request);
    }

    @Override
    public Optional<Activity> consultarPorId(Long id) {
        return activityRepositoryPort.obtenerPorId(id);
    }

    @Override
    public List<Activity> listarTodas() {
        return activityRepositoryPort.listarTodas();
    }

    @Override
    public List<Activity> buscar(FiltroActivityDto filtro) {
        if (filtro == null || filtro.estaVacio()) {
            return activityRepositoryPort.listarTodas();
        }
        return activityRepositoryPort.buscar(filtro);
    }

    @Override
    public void eliminar(Long id) {
        activityRepositoryPort.eliminar(id);
    }

    @Override
    public Activity publicar(Long id) {
        Activity activity = obtenerOFallar(id);
        if (activity.getEstado() == EstadoActivity.PUBLISHED) {
            throw new IllegalStateException(
                    "La actividad ya está publicada. Cualquier cambio posterior republica una nueva versión, "
                            + "pero no afecta a quien ya la tenga asignada.");
        }

        List<ActivityContentItem> contenido = activityRepositoryPort.listarContenido(id);
        ActivityTypeDefinition tipo = activityTypeDefinitionRepositoryPort
                .obtenerPorId(activity.getActivityTypeId())
                .orElseThrow(() -> new IllegalStateException("El tipo de actividad asociado ya no existe"));

        activityHandlerRegistry.obtener(tipo.getType()).validarPublicacion(contenido);

        Activity publicada = activityRepositoryPort.publicar(id);
        publicarEventos(publicada);
        return publicada;
    }

    @Override
    public Activity archivar(Long id) {
        return activityRepositoryPort.archivar(id);
    }

    @Override
    public Activity duplicar(Long id) {
        obtenerOFallar(id);
        return activityRepositoryPort.duplicar(id);
    }

    @Override
    public ActivityContentItem agregarContenido(Long activityId, AgregarContenidoActivityDto request) {
        obtenerOFallar(activityId);
        return activityRepositoryPort.agregarContenido(activityId, request);
    }

    @Override
    public List<ActivityContentItem> listarContenido(Long activityId) {
        return activityRepositoryPort.listarContenido(activityId);
    }

    @Override
    public void eliminarContenido(Long activityId, Long contentItemId) {
        activityRepositoryPort.eliminarContenido(activityId, contentItemId);
    }

    @Override
    @Transactional
    public List<ActivityContentItem> reordenar(Long activityId, ReordenarContenidoActivityDto request) {
        // El adapter hace la actualización en dos fases (órdenes temporales
        // negativos y luego los finales) para no chocar con el UNIQUE(activity_id,
        // orden) real de la tabla — ver comentario en ActivityJpaAdapter#reordenar.
        // Sin una transacción que envuelva ambas fases, un fallo entre la primera
        // y la segunda dejaría los `orden` en sus valores temporales negativos.
        return activityRepositoryPort.reordenar(activityId, request);
    }

    private Activity obtenerOFallar(Long id) {
        return activityRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
    }

    /**
     * Registra en el agregado los eventos de dominio correspondientes al
     * cambio recién persistido y los publica, siguiendo el patrón ya
     * existente (puerto + adapter -> ApplicationEventPublisher de Spring).
     */
    private void publicarEventos(Activity activity) {
        activity.registrarEventoPublicacion();
        activity.getDomainEvents().forEach(activityEventPublisher::publish);
        activity.clearDomainEvents();
    }
}
