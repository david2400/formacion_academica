package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ActualizarActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.AgregarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.CrearActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.FiltroActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.OrdenContenidoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ReordenarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ActivityMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityContentItemEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityVersionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityContentItemJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivitySpecifications;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityVersionJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.PreguntaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ActivityJpaAdapter implements ActivityRepositoryPort {

    private final ActivityJpaRepository activityJpaRepository;
    private final ActivityVersionJpaRepository activityVersionJpaRepository;
    private final ActivityContentItemJpaRepository activityContentItemJpaRepository;
    private final PreguntaJpaRepository preguntaJpaRepository;
    private final ActivityMapper activityMapper;

    @Override
    public Activity guardar(CrearActivityDto dto) {
        ActivityEntity entity = activityMapper.toEntity(dto);
        entity.setEsObligatoria(dto.getEsObligatoria() == null || dto.getEsObligatoria());
        entity.setPermiteReintentos(dto.getPermiteReintentos() == null || dto.getPermiteReintentos());
        return activityMapper.toDomainModel(activityJpaRepository.save(entity));
    }

    @Override
    public Optional<Activity> obtenerPorId(Long id) {
        return activityJpaRepository.findById(id).map(activityMapper::toDomainModel);
    }

    @Override
    public List<Activity> listarTodas() {
        return activityMapper.toDomainModelList(activityJpaRepository.findAll());
    }

    @Override
    public List<Activity> buscar(FiltroActivityDto filtro) {
        return activityMapper.toDomainModelList(
                activityJpaRepository.findAll(ActivitySpecifications.desdeFiltro(filtro)));
    }

    @Override
    public Activity actualizar(ActualizarActivityDto dto) {
        ActivityEntity existing = buscarEntidad(dto.getId());
        existing.setTitulo(dto.getTitulo());
        existing.setDescripcion(dto.getDescripcion());
        existing.setInstrucciones(dto.getInstrucciones());
        existing.setDuracionEstimadaMinutos(dto.getDuracionEstimadaMinutos());
        existing.setPuntos(dto.getPuntos());
        existing.setNotaMinima(dto.getNotaMinima());
        if (dto.getEsObligatoria() != null) {
            existing.setEsObligatoria(dto.getEsObligatoria());
        }
        if (dto.getPermiteReintentos() != null) {
            existing.setPermiteReintentos(dto.getPermiteReintentos());
        }
        existing.setMaxIntentos(dto.getMaxIntentos());
        return activityMapper.toDomainModel(activityJpaRepository.save(existing));
    }

    @Override
    public void eliminar(Long id) {
        ActivityEntity existing = buscarEntidad(id);
        existing.setEliminado(true);
        activityJpaRepository.save(existing);
    }

    @Override
    public Activity publicar(Long id) {
        ActivityEntity existing = buscarEntidad(id);
        List<ActivityContentItemEntity> contenido =
                activityContentItemJpaRepository.findByActivityIdOrderByOrdenAsc(id);

        int siguienteVersion = activityVersionJpaRepository.countByActivityId(id) + 1;

        ActivityVersionEntity version = ActivityVersionEntity.builder()
                .activityId(id)
                .numeroVersion(siguienteVersion)
                .snapshot(construirSnapshot(existing, contenido))
                .publicadoEn(Instant.now())
                .publicadoPor(1L) // ver AuditInfo: usuario autenticado real no está conectado todavía
                .build();
        version = activityVersionJpaRepository.save(version);

        existing.setEstado(EstadoActivity.PUBLISHED);
        existing.setVersionActualId(version.getId());
        existing = activityJpaRepository.save(existing);

        return activityMapper.toDomainModel(existing);
    }

    private Map<String, Object> construirSnapshot(ActivityEntity activity, List<ActivityContentItemEntity> contenido) {
        Map<String, Object> snapshot = new HashMap<>();
        snapshot.put("titulo", activity.getTitulo());
        snapshot.put("descripcion", activity.getDescripcion());
        snapshot.put("instrucciones", activity.getInstrucciones());
        snapshot.put("puntos", activity.getPuntos());
        snapshot.put("notaMinima", activity.getNotaMinima());
        snapshot.put("maxIntentos", activity.getMaxIntentos());

        List<Map<String, Object>> items = new ArrayList<>();
        for (ActivityContentItemEntity item : contenido) {
            Map<String, Object> itemSnapshot = new HashMap<>();
            itemSnapshot.put("contentItemId", item.getId());
            itemSnapshot.put("orden", item.getOrden());
            itemSnapshot.put("contentKind", item.getContentKind().name());
            itemSnapshot.put("preguntaId", item.getPreguntaId());
            itemSnapshot.put("texto", item.getTexto());
            itemSnapshot.put("contenidoExterno", item.getContenidoExterno());
            itemSnapshot.put("puntos", item.getPuntos());
            items.add(itemSnapshot);
        }
        snapshot.put("contenido", items);
        return snapshot;
    }

    @Override
    public Activity archivar(Long id) {
        ActivityEntity existing = buscarEntidad(id);
        existing.setEstado(EstadoActivity.ARCHIVED);
        return activityMapper.toDomainModel(activityJpaRepository.save(existing));
    }

    @Override
    public Activity duplicar(Long id) {
        ActivityEntity original = buscarEntidad(id);

        ActivityEntity copia = ActivityEntity.builder()
                .activityTypeId(original.getActivityTypeId())
                .titulo(original.getTitulo() + " (copia)")
                .descripcion(original.getDescripcion())
                .instrucciones(original.getInstrucciones())
                .estado(EstadoActivity.DRAFT)
                .duracionEstimadaMinutos(original.getDuracionEstimadaMinutos())
                .puntos(original.getPuntos())
                .notaMinima(original.getNotaMinima())
                .esObligatoria(original.isEsObligatoria())
                .permiteReintentos(original.isPermiteReintentos())
                .maxIntentos(original.getMaxIntentos())
                .build();
        copia.setEliminado(false);
        copia = activityJpaRepository.save(copia);

        List<ActivityContentItemEntity> contenidoOriginal =
                activityContentItemJpaRepository.findByActivityIdOrderByOrdenAsc(id);
        for (ActivityContentItemEntity item : contenidoOriginal) {
            ActivityContentItemEntity nuevoItem = ActivityContentItemEntity.builder()
                    .activityId(copia.getId())
                    .orden(item.getOrden())
                    .contentKind(item.getContentKind())
                    .preguntaId(item.getPreguntaId())
                    .texto(item.getTexto())
                    .contenidoExterno(item.getContenidoExterno())
                    .puntos(item.getPuntos())
                    .build();
            nuevoItem.setEliminado(false);
            activityContentItemJpaRepository.save(nuevoItem);
        }

        return activityMapper.toDomainModel(copia);
    }

    @Override
    public ActivityContentItem agregarContenido(Long activityId, AgregarContenidoActivityDto dto) {
        buscarEntidad(activityId); // valida que la actividad exista

        if (dto.getContentKind() == ContentKind.QUESTION) {
            if (dto.getPreguntaId() == null) {
                throw new IllegalArgumentException("Debe indicar la pregunta a agregar");
            }
            if (!preguntaJpaRepository.existsById(dto.getPreguntaId())) {
                throw new IllegalArgumentException("La pregunta indicada no existe o fue eliminada");
            }
        } else if (dto.getContentKind() == ContentKind.TEXT && dto.getTexto() == null) {
            throw new IllegalArgumentException("Debe indicar el contenido de texto a agregar");
        } else if (dto.getContentKind() == ContentKind.EXTERNAL) {
            validarContenidoExterno(dto.getContenidoExterno());
        }

        int siguienteOrden = activityContentItemJpaRepository.countByActivityId(activityId) + 1;

        ActivityContentItemEntity entity = ActivityContentItemEntity.builder()
                .activityId(activityId)
                .orden(siguienteOrden)
                .contentKind(dto.getContentKind())
                .preguntaId(dto.getPreguntaId())
                .texto(dto.getTexto())
                .contenidoExterno(dto.getContenidoExterno())
                .puntos(dto.getPuntos())
                .build();
        entity.setEliminado(false);

        return activityMapper.toDomainModel(activityContentItemJpaRepository.save(entity));
    }

    /** Valores válidos de "tipo" dentro del JSON de contenido externo. */
    private static final java.util.Set<String> TIPOS_CONTENIDO_EXTERNO_VALIDOS =
            java.util.Set.of("VIDEO", "EMBED", "LINK");

    /**
     * Validación de entrada para contentKind=EXTERNAL (video, embed o enlace
     * de terceros). No valida contra un allowlist de dominios (no fue parte
     * del alcance solicitado); sí exige esquema https como higiene mínima de
     * seguridad — evita que un valor como {@code javascript:...} o una URL
     * http sin cifrar termine en un iframe/enlace mostrado a estudiantes
     * (CLAUDE.md §82/§83: la UX no sustituye seguridad, pero sí puede evitar
     * los casos triviales).
     */
    private void validarContenidoExterno(java.util.Map<String, Object> contenidoExterno) {
        if (contenidoExterno == null) {
            throw new IllegalArgumentException("Debe indicar el contenido externo a agregar");
        }
        Object tipo = contenidoExterno.get("tipo");
        if (!(tipo instanceof String) || !TIPOS_CONTENIDO_EXTERNO_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException("El tipo de contenido externo debe ser VIDEO, EMBED o LINK");
        }
        Object url = contenidoExterno.get("url");
        if (!(url instanceof String) || ((String) url).isBlank()) {
            throw new IllegalArgumentException("Debe indicar la URL del contenido externo");
        }
        if (!((String) url).startsWith("https://")) {
            throw new IllegalArgumentException("La URL del contenido externo debe ser segura (https)");
        }
    }

    @Override
    public List<ActivityContentItem> listarContenido(Long activityId) {
        return activityMapper.toContentDomainModelList(
                activityContentItemJpaRepository.findByActivityIdOrderByOrdenAsc(activityId));
    }

    @Override
    public void eliminarContenido(Long activityId, Long contentItemId) {
        activityContentItemJpaRepository.deleteByActivityIdAndId(activityId, contentItemId);
    }

    @Override
    public List<ActivityContentItem> reordenar(Long activityId, ReordenarContenidoActivityDto dto) {
        Map<Long, Integer> nuevosOrdenes = new HashMap<>();
        for (OrdenContenidoDto item : dto.getItems()) {
            nuevosOrdenes.put(item.getContentItemId(), item.getOrden());
        }

        List<ActivityContentItemEntity> items =
                activityContentItemJpaRepository.findByActivityIdOrderByOrdenAsc(activityId);
        for (ActivityContentItemEntity item : items) {
            Integer nuevoOrden = nuevosOrdenes.get(item.getId());
            if (nuevoOrden != null) {
                item.setOrden(nuevoOrden);
            }
        }
        activityContentItemJpaRepository.saveAll(items);

        return activityMapper.toContentDomainModelList(
                activityContentItemJpaRepository.findByActivityIdOrderByOrdenAsc(activityId));
    }

    @Override
    public Optional<ActivityContentItem> obtenerContenidoPorId(Long contentItemId) {
        return activityContentItemJpaRepository.findById(contentItemId)
                .map(activityMapper::toDomainModel);
    }

    private ActivityEntity buscarEntidad(Long id) {
        return activityJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Actividad no encontrada"));
    }
}
