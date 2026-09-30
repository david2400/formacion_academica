package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ActualizarInfoBasicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.ExamNotFoundException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.ExamenEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ExamenJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.ExamenTematicaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ExamenTematicaJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExamenJpaAdapter implements ExamenRepositoryPort {

    private final ExamenJpaRepository examenJpaRepository;
    private final ExamenTematicaJpaRepository examenTematicaJpaRepository;
    private final ExamenMapper examenMapper;

    @Override
    @Transactional
    public Examen save(Examen examen) {
        log.debug("Guardando examen: {}", examen.getNombre());
        
        // Implementación mínima - retornar el mismo examen por ahora
        return examen;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Examen> findById(Long id) {
        log.debug("Buscando examen por ID: {}", id);

        return examenJpaRepository.findById(id).map(examenMapper::toDomainModel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("Eliminando examen por ID: {}", id);
        examenJpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Examen> search(ExamSearchCriteria criteria, Pageable pageable) {
        log.debug("Buscando exámenes con criterios: {}", criteria);
        
        // Implementación mínima - retornar página vacía por ahora
        return Page.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        log.debug("Verificando si existe examen con ID: {}", id);
        return examenJpaRepository.existsById(id);
    }
    
    // Métodos adicionales para compatibilidad con el servicio - implementaciones básicas
    @Override
    @Transactional
    public Examen guardar(CrearExamenDto request) {
        log.debug("Guardando examen desde DTO: {}", request.getNombre());

        ExamenEntity entity = examenMapper.toEntity(request);

        ExamenEntity saved = examenJpaRepository.save(entity);
        return examenMapper.toDomainModel(saved);
    }

    @Override
    @Transactional
    public Examen actualizarInfoBasica(Long id, ActualizarInfoBasicaExamenDto dto) {
        log.debug("Actualizando información básica del examen ID: {}", id);

        ExamenEntity entity = examenJpaRepository.findById(id)
                .orElseThrow(() -> new ExamNotFoundException(id));

        examenMapper.applyInfoBasica(entity, dto);

        ExamenEntity saved = examenJpaRepository.save(entity);
        return examenMapper.toDomainModel(saved);
    }
    
    @Override
    public CalificacionPersonalizadaDto registrarCalificacion(RegistrarCalificacionPersonalizadaDto request) {
        // Implementación por defecto - lanzar excepción para que el servicio lo maneje
        throw new UnsupportedOperationException("Método registrarCalificacion no implementado en este adaptador");
    }
    
    // Métodos de compatibilidad con Exam (transición)
    @Override
    public Exam save(Exam exam) {
        log.debug("Guardando exam: {}", exam.getName());
        
        // Implementación mínima - retornar el mismo exam por ahora
        return exam;
    }
    
    @Override
    public Optional<Exam> findExamById(Long id) {
        // Implementación mínima - retornar empty por ahora
        return Optional.empty();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Exam> searchExams(ExamSearchCriteria criteria, Pageable pageable) {
        // El agregado Exam (code/subject/status/timeConfig/preguntas/criterios
        // propios) nunca se conectó a la base de datos -mismo motivo que
        // save(Exam)/findExamById de arriba, siempre vacíos-, así que este
        // listado (el único consumidor real de searchExams, vía
        // BuscarExamenesUseCase -> GET /examenes) se arma desde el camino
        // legado (ExamenEntity/Examen), igual que actualizarInfoBasica/
        // findById. Cada fila se traduce a un Exam "sintético" solo para
        // poder reutilizar ExamResponse.fromDomain sin duplicar ese DTO;
        // code/subject/gradeLevel/timeConfig/preguntas/criterios quedan
        // vacíos porque el modelo legado no los tiene (las asignaciones
        // reales viven en examen_criterio/examen_pregunta/examen_tematica).
        String searchText = criteria != null ? criteria.getSearchText() : null;
        Long tematicaId = criteria != null ? criteria.getTematicaId() : null;

        Page<ExamenEntity> page;
        if (tematicaId != null) {
            // El modelo legado no tiene tema propio: el filtro va por la
            // relación examenes_tematicas. searchText no se combina acá -no
            // hay un caso de uso hoy que pida ambos a la vez-.
            List<Long> examenIds = examenTematicaJpaRepository.findByTematicaId(tematicaId).stream()
                    .map(ExamenTematicaEntity::getExamenId)
                    .toList();
            page = examenIds.isEmpty() ? Page.empty(pageable) : examenJpaRepository.findByIdIn(examenIds, pageable);
        } else if (searchText != null && !searchText.isBlank()) {
            page = examenJpaRepository.findByNombreContainingIgnoreCase(searchText, pageable);
        } else {
            page = examenJpaRepository.findAll(pageable);
        }

        return page.map(entity -> {
            Exam exam = new Exam();
            exam.setId(entity.getId());
            exam.setName(entity.getNombre());
            // code nunca null: ExamManager (frontend) hace code.toLowerCase()
            // sin chequear null al filtrar la tabla.
            exam.setCode("");
            exam.setCreatedAt(entity.getCreatedAt());
            exam.setUpdatedAt(entity.getUpdatedAt());
            return exam;
        });
    }
}
