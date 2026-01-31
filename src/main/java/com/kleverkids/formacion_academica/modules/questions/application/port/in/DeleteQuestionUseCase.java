package com.kleverkids.formacion_academica.modules.questions.application.port.in;

import java.util.UUID;

public interface DeleteQuestionUseCase {
    
    void delete(UUID id);
}
