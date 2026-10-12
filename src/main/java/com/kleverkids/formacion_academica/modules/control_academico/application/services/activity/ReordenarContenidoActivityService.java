package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ReordenarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ReordenarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReordenarContenidoActivityService implements ReordenarContenidoActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

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
}
