package com.kleverkids.formacion_academica.modules.control_academico.application.services.student_progress;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.student_progress.ConsultarProgresoEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.student_progress.StudentSequenceProgressRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.student_progress.StudentSequenceProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarProgresoEstudianteService implements ConsultarProgresoEstudianteUseCase {

    private final StudentSequenceProgressRepositoryPort studentSequenceProgressRepositoryPort;
    private final RecalcularProgresoEstudianteService recalcularProgresoEstudianteService;

    @Override
    public StudentSequenceProgress consultar(Long estudianteId, Long sequenceId) {
        return studentSequenceProgressRepositoryPort.obtenerPorEstudianteYSecuencia(estudianteId, sequenceId)
                .orElseGet(() -> recalcularProgresoEstudianteService.recalcular(estudianteId, sequenceId));
    }
}
