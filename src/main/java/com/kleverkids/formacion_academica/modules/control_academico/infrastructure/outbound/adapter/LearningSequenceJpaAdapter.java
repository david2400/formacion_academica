package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ActualizarLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.AgregarItemSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.CrearLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.DefinirDependenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.FiltroLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.OrdenItemDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ReordenarItemsSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.EstadoLearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.LearningSequenceMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.ActivityDependencyRuleEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceItemEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence.LearningSequenceVersionEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityDependencyRuleJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.LearningSequenceItemJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.LearningSequenceJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.LearningSequenceSpecifications;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.LearningSequenceVersionJpaRepository;
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
public class LearningSequenceJpaAdapter implements LearningSequenceRepositoryPort {

    private final LearningSequenceJpaRepository learningSequenceJpaRepository;
    private final LearningSequenceVersionJpaRepository learningSequenceVersionJpaRepository;
    private final LearningSequenceItemJpaRepository learningSequenceItemJpaRepository;
    private final ActivityDependencyRuleJpaRepository activityDependencyRuleJpaRepository;
    private final LearningSequenceMapper learningSequenceMapper;

    @Override
    public LearningSequence guardar(CrearLearningSequenceDto dto) {
        LearningSequenceEntity entity = learningSequenceMapper.toEntity(dto);
        return learningSequenceMapper.toDomainModel(learningSequenceJpaRepository.save(entity));
    }

    @Override
    public Optional<LearningSequence> obtenerPorId(Long id) {
        return learningSequenceJpaRepository.findById(id).map(learningSequenceMapper::toDomainModel);
    }

    @Override
    public List<LearningSequence> listarTodas() {
        return learningSequenceMapper.toDomainModelList(learningSequenceJpaRepository.findAll());
    }

    @Override
    public List<LearningSequence> buscar(FiltroLearningSequenceDto filtro) {
        return learningSequenceMapper.toDomainModelList(
                learningSequenceJpaRepository.findAll(LearningSequenceSpecifications.desdeFiltro(filtro)));
    }

    @Override
    public LearningSequence actualizar(ActualizarLearningSequenceDto dto) {
        LearningSequenceEntity existing = buscarEntidad(dto.getId());
        existing.setTitulo(dto.getTitulo());
        existing.setDescripcion(dto.getDescripcion());
        return learningSequenceMapper.toDomainModel(learningSequenceJpaRepository.save(existing));
    }

    @Override
    public void eliminar(Long id) {
        LearningSequenceEntity existing = buscarEntidad(id);
        existing.setEliminado(true);
        learningSequenceJpaRepository.save(existing);
    }

    @Override
    public LearningSequence publicar(Long id) {
        LearningSequenceEntity existing = buscarEntidad(id);
        List<LearningSequenceItemEntity> items = learningSequenceItemJpaRepository.findBySequenceIdOrderByOrdenAsc(id);

        int siguienteVersion = learningSequenceVersionJpaRepository.countBySequenceId(id) + 1;

        LearningSequenceVersionEntity version = LearningSequenceVersionEntity.builder()
                .sequenceId(id)
                .numeroVersion(siguienteVersion)
                .snapshot(construirSnapshot(existing, items))
                .publicadoEn(Instant.now())
                .publicadoPor(1L) // ver AuditInfo: usuario autenticado real no está conectado todavía
                .build();
        version = learningSequenceVersionJpaRepository.save(version);

        existing.setEstado(EstadoLearningSequence.PUBLISHED);
        existing.setVersionActualId(version.getId());
        existing = learningSequenceJpaRepository.save(existing);

        return learningSequenceMapper.toDomainModel(existing);
    }

    private Map<String, Object> construirSnapshot(LearningSequenceEntity sequence, List<LearningSequenceItemEntity> items) {
        Map<String, Object> snapshot = new HashMap<>();
        snapshot.put("titulo", sequence.getTitulo());
        snapshot.put("descripcion", sequence.getDescripcion());

        List<Map<String, Object>> itemsSnapshot = new ArrayList<>();
        for (LearningSequenceItemEntity item : items) {
            Map<String, Object> itemMap = new HashMap<>();
            itemMap.put("itemId", item.getId());
            itemMap.put("parentItemId", item.getParentItemId());
            itemMap.put("itemType", item.getItemType().name());
            itemMap.put("activityId", item.getActivityId());
            itemMap.put("tituloGrupo", item.getTituloGrupo());
            itemMap.put("orden", item.getOrden());
            itemMap.put("esObligatoria", item.isEsObligatoria());
            itemMap.put("disponibleDesde", item.getDisponibleDesde());
            itemMap.put("fechaLimite", item.getFechaLimite());

            List<Map<String, Object>> dependencias = new ArrayList<>();
            for (ActivityDependencyRuleEntity regla : activityDependencyRuleJpaRepository.findByItemId(item.getId())) {
                Map<String, Object> reglaMap = new HashMap<>();
                reglaMap.put("dependsOnItemId", regla.getDependsOnItemId());
                reglaMap.put("ruleType", regla.getRuleType().name());
                reglaMap.put("ruleValorNumerico", regla.getRuleValorNumerico());
                reglaMap.put("ruleValorFecha", regla.getRuleValorFecha());
                dependencias.add(reglaMap);
            }
            itemMap.put("dependencias", dependencias);

            itemsSnapshot.add(itemMap);
        }
        snapshot.put("items", itemsSnapshot);
        return snapshot;
    }

    @Override
    public LearningSequence archivar(Long id) {
        LearningSequenceEntity existing = buscarEntidad(id);
        existing.setEstado(EstadoLearningSequence.ARCHIVED);
        return learningSequenceMapper.toDomainModel(learningSequenceJpaRepository.save(existing));
    }

    @Override
    public LearningSequence duplicar(Long id) {
        LearningSequenceEntity original = buscarEntidad(id);

        LearningSequenceEntity copia = LearningSequenceEntity.builder()
                .titulo(original.getTitulo() + " (copia)")
                .descripcion(original.getDescripcion())
                .estado(EstadoLearningSequence.DRAFT)
                .build();
        copia.setEliminado(false);
        copia = learningSequenceJpaRepository.save(copia);

        List<LearningSequenceItemEntity> originales =
                learningSequenceItemJpaRepository.findBySequenceIdOrderByOrdenAsc(id);
        Map<Long, Long> idViejoANuevo = new HashMap<>();

        // nivel raíz primero, para poder remapear el padre de los items anidados
        for (LearningSequenceItemEntity item : originales) {
            if (item.getParentItemId() == null) {
                LearningSequenceItemEntity copiaItem = copiarItem(item, copia.getId(), null);
                copiaItem = learningSequenceItemJpaRepository.save(copiaItem);
                idViejoANuevo.put(item.getId(), copiaItem.getId());
            }
        }
        for (LearningSequenceItemEntity item : originales) {
            if (item.getParentItemId() != null) {
                Long nuevoPadre = idViejoANuevo.get(item.getParentItemId());
                LearningSequenceItemEntity copiaItem = copiarItem(item, copia.getId(), nuevoPadre);
                copiaItem = learningSequenceItemJpaRepository.save(copiaItem);
                idViejoANuevo.put(item.getId(), copiaItem.getId());
            }
        }

        // dependencias: solo se copian las que quedan completamente dentro de la secuencia duplicada
        for (LearningSequenceItemEntity item : originales) {
            for (ActivityDependencyRuleEntity regla : activityDependencyRuleJpaRepository.findByItemId(item.getId())) {
                Long nuevoItemId = idViejoANuevo.get(regla.getItemId());
                Long nuevoDependeDeId = idViejoANuevo.get(regla.getDependsOnItemId());
                if (nuevoItemId != null && nuevoDependeDeId != null) {
                    ActivityDependencyRuleEntity nuevaRegla = ActivityDependencyRuleEntity.builder()
                            .itemId(nuevoItemId)
                            .dependsOnItemId(nuevoDependeDeId)
                            .ruleType(regla.getRuleType())
                            .ruleValorNumerico(regla.getRuleValorNumerico())
                            .ruleValorFecha(regla.getRuleValorFecha())
                            .build();
                    activityDependencyRuleJpaRepository.save(nuevaRegla);
                }
            }
        }

        return learningSequenceMapper.toDomainModel(copia);
    }

    private LearningSequenceItemEntity copiarItem(LearningSequenceItemEntity original, Long nuevaSequenceId, Long nuevoParentId) {
        LearningSequenceItemEntity copia = LearningSequenceItemEntity.builder()
                .sequenceId(nuevaSequenceId)
                .parentItemId(nuevoParentId)
                .itemType(original.getItemType())
                .activityId(original.getActivityId())
                .tituloGrupo(original.getTituloGrupo())
                .orden(original.getOrden())
                .esObligatoria(original.isEsObligatoria())
                .disponibleDesde(original.getDisponibleDesde())
                .fechaLimite(original.getFechaLimite())
                .build();
        copia.setEliminado(false);
        return copia;
    }

    @Override
    public LearningSequenceItem agregarItem(Long sequenceId, AgregarItemSequenceDto dto) {
        int siguienteOrden = dto.getParentItemId() == null
                ? learningSequenceItemJpaRepository.countBySequenceIdAndParentItemIdIsNull(sequenceId) + 1
                : learningSequenceItemJpaRepository.countBySequenceIdAndParentItemId(sequenceId, dto.getParentItemId()) + 1;

        LearningSequenceItemEntity entity = LearningSequenceItemEntity.builder()
                .sequenceId(sequenceId)
                .parentItemId(dto.getParentItemId())
                .itemType(dto.getItemType())
                .activityId(dto.getItemType() == TipoItemSecuencia.ACTIVITY ? dto.getActivityId() : null)
                .tituloGrupo(dto.getItemType() == TipoItemSecuencia.GROUP ? dto.getTituloGrupo() : null)
                .orden(siguienteOrden)
                .esObligatoria(dto.getEsObligatoria() == null || dto.getEsObligatoria())
                .disponibleDesde(toInstant(dto.getDisponibleDesde()))
                .fechaLimite(toInstant(dto.getFechaLimite()))
                .build();
        entity.setEliminado(false);

        return learningSequenceMapper.toDomainModel(learningSequenceItemJpaRepository.save(entity));
    }

    @Override
    public List<LearningSequenceItem> listarItems(Long sequenceId) {
        return learningSequenceMapper.toItemDomainModelList(
                learningSequenceItemJpaRepository.findBySequenceIdOrderByOrdenAsc(sequenceId));
    }

    @Override
    public Optional<LearningSequenceItem> obtenerItem(Long itemId) {
        return learningSequenceItemJpaRepository.findById(itemId).map(learningSequenceMapper::toDomainModel);
    }

    @Override
    public void eliminarItem(Long sequenceId, Long itemId) {
        LearningSequenceItemEntity item = learningSequenceItemJpaRepository.findById(itemId)
                .filter(i -> i.getSequenceId().equals(sequenceId))
                .orElseThrow(() -> new IllegalArgumentException("Item no encontrado en esta secuencia"));

        if (item.getItemType() == TipoItemSecuencia.GROUP) {
            for (LearningSequenceItemEntity hijo : learningSequenceItemJpaRepository.findByParentItemId(itemId)) {
                activityDependencyRuleJpaRepository.deleteByItemIdOrDependsOnItemId(hijo.getId(), hijo.getId());
                learningSequenceItemJpaRepository.delete(hijo);
            }
        }
        activityDependencyRuleJpaRepository.deleteByItemIdOrDependsOnItemId(itemId, itemId);
        learningSequenceItemJpaRepository.delete(item);
    }

    @Override
    public List<LearningSequenceItem> reordenar(Long sequenceId, ReordenarItemsSequenceDto dto) {
        Map<Long, Integer> nuevosOrdenes = new HashMap<>();
        for (OrdenItemDto item : dto.getItems()) {
            nuevosOrdenes.put(item.getItemId(), item.getOrden());
        }

        List<LearningSequenceItemEntity> nivel = dto.getParentItemId() == null
                ? learningSequenceItemJpaRepository.findBySequenceIdAndParentItemIdIsNullOrderByOrdenAsc(sequenceId)
                : learningSequenceItemJpaRepository.findBySequenceIdAndParentItemIdOrderByOrdenAsc(sequenceId, dto.getParentItemId());

        for (LearningSequenceItemEntity item : nivel) {
            Integer nuevoOrden = nuevosOrdenes.get(item.getId());
            if (nuevoOrden != null) {
                item.setOrden(nuevoOrden);
            }
        }
        learningSequenceItemJpaRepository.saveAll(nivel);

        List<LearningSequenceItemEntity> actualizado = dto.getParentItemId() == null
                ? learningSequenceItemJpaRepository.findBySequenceIdAndParentItemIdIsNullOrderByOrdenAsc(sequenceId)
                : learningSequenceItemJpaRepository.findBySequenceIdAndParentItemIdOrderByOrdenAsc(sequenceId, dto.getParentItemId());
        return learningSequenceMapper.toItemDomainModelList(actualizado);
    }

    @Override
    public ActivityDependencyRule definirDependencia(Long itemId, DefinirDependenciaDto dto) {
        ActivityDependencyRuleEntity entity = ActivityDependencyRuleEntity.builder()
                .itemId(itemId)
                .dependsOnItemId(dto.getDependsOnItemId())
                .ruleType(dto.getRuleType())
                .ruleValorNumerico(dto.getRuleValorNumerico())
                .ruleValorFecha(toInstant(dto.getRuleValorFecha()))
                .build();
        return learningSequenceMapper.toDomainModel(activityDependencyRuleJpaRepository.save(entity));
    }

    @Override
    public List<ActivityDependencyRule> listarDependencias(Long itemId) {
        return learningSequenceMapper.toDependencyDomainModelList(
                activityDependencyRuleJpaRepository.findByItemId(itemId));
    }

    @Override
    public void eliminarDependencia(Long itemId, Long dependencyId) {
        activityDependencyRuleJpaRepository.deleteByItemIdAndId(itemId, dependencyId);
    }

    private LearningSequenceEntity buscarEntidad(Long id) {
        return learningSequenceJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Secuencia no encontrada"));
    }

    private Instant toInstant(java.time.LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant();
    }
}
