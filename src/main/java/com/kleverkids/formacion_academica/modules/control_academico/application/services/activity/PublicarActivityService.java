package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.PublicarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler.ActivityHandlerRegistry;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicarActivityService implements PublicarActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;
    private final ActivityHandlerRegistry activityHandlerRegistry;
    private final ActivityEventPublisher activityEventPublisher;
    private final ActivitySoporte activitySoporte;

    @Override
    public Activity publicar(Long id) {
        Activity activity = activitySoporte.obtenerOFallar(id);
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
