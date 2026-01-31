package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.ExamResultResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen.SubmitExamCommand;

import java.util.UUID;

public interface SubmitExamUseCase {
    
    ExamResultResponse submit(UUID examId, SubmitExamCommand command);
}
