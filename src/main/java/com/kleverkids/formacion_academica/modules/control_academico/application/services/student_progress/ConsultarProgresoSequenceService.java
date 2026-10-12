package com.kleverkids.formacion_academica.modules.control_academico.application.services.student_progress;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.student_progress.ConsultarProgresoSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.student_progress.StudentSequenceProgressRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarProgresoSequenceService implements ConsultarProgresoSequenceUseCase {

    private final StudentSequenceProgressRepositoryPort studentSequenceProgressRepositoryPort;

    @Override
    public List<StudentSequenceProgress> consultarPorSecuencia(Long sequenceId) {
        return studentSequenceProgressRepositoryPort.listarPorSecuencia(sequenceId);
    }
}
