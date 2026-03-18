package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen.ExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.CrearExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamSearchCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.RegistrarCalificacionPersonalizadaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Examen;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamenMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ExamPersistenceMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.CalificacionPersonalizadaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes.ExamEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.CalificacionPersonalizadaJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamenJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.repository.ExamJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;


@RequiredArgsConstructor
@Component
public class ExamenJpaAdapter implements ExamRepositoryPort, ExamenRepositoryPort {

    // Repositorios del sistema en inglés
    private final ExamJpaRepository examJpaRepository;
    private final ExamPersistenceMapper examPersistenceMapper;
    
    // Repositorios del sistema en español
    private final ExamenJpaRepository examenJpaRepository;
    private final CalificacionPersonalizadaJpaRepository calificacionRepository;
    private final ExamenMapper examenMapper;

    // Implementación de ExamRepositoryPort (inglés)
    @Override
    public Exam save(Exam exam) {
        ExamEntity entity = examPersistenceMapper.toEntity(exam);
        ExamEntity saved = examJpaRepository.save(entity);
        return examPersistenceMapper.toDomain(saved);
    }
    
    @Override
    public Optional<Exam> findById(Long id) {
        return examJpaRepository.findById(id).map(examPersistenceMapper::toDomain);
    }
    
    @Override
    public void deleteById(Long id) {
        examJpaRepository.deleteById(id);
    }
    
    @Override
    public Page<Exam> search(ExamSearchCriteria criteria, Pageable pageable) {
        Page<ExamEntity> entities = examJpaRepository.search(
            criteria.status(),
            criteria.subject(),
            criteria.gradeLevel(),
            criteria.searchText(),
            pageable
        );
        return entities.map(examPersistenceMapper::toDomain);
    }
    
    @Override
    public boolean existsById(Long id) {
        return examJpaRepository.existsById(id);
    }

    // Implementación de ExamenRepositoryPort (español)
    @Override
    public Examen guardar(CrearExamenDto request) {
        return examenMapper.toDomainModel(examenJpaRepository.save(examenMapper.toEntity(request)));
    }

    @Override
    public CalificacionPersonalizadaDto registrarCalificacion(RegistrarCalificacionPersonalizadaDto request) {
        CalificacionPersonalizadaEntity entity = examenMapper.toCalificacionEntity(request);
        return examenMapper.toCalificacionDto(calificacionRepository.save(entity));
    }
}
