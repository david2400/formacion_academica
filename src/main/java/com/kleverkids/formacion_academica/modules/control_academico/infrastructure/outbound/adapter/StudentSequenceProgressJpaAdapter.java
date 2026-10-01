package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.student_progress.StudentSequenceProgressRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.StudentSequenceProgressMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.student_progress.StudentSequenceProgressEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.StudentSequenceProgressJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class StudentSequenceProgressJpaAdapter implements StudentSequenceProgressRepositoryPort {

    private final StudentSequenceProgressJpaRepository studentSequenceProgressJpaRepository;
    private final StudentSequenceProgressMapper studentSequenceProgressMapper;

    @Override
    public Optional<StudentSequenceProgress> obtenerPorEstudianteYSecuencia(Long estudianteId, Long sequenceId) {
        return studentSequenceProgressJpaRepository.findByEstudianteIdAndSequenceId(estudianteId, sequenceId)
                .map(studentSequenceProgressMapper::toDomainModel);
    }

    /** Upsert: si ya existe fila para (estudianteId, sequenceId) se reutiliza su id para actualizarla en vez de duplicarla. */
    @Override
    public StudentSequenceProgress guardar(StudentSequenceProgress progreso) {
        StudentSequenceProgressEntity entity = studentSequenceProgressMapper.toEntity(progreso);
        studentSequenceProgressJpaRepository
                .findByEstudianteIdAndSequenceId(progreso.getEstudianteId(), progreso.getSequenceId())
                .ifPresent(existing -> entity.setId(existing.getId()));
        return studentSequenceProgressMapper.toDomainModel(studentSequenceProgressJpaRepository.save(entity));
    }

    @Override
    public List<StudentSequenceProgress> listarPorSecuencia(Long sequenceId) {
        return studentSequenceProgressMapper.toDomainModelList(
                studentSequenceProgressJpaRepository.findBySequenceId(sequenceId));
    }
}
