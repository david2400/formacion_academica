package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.util.List;
import java.util.UUID;

public record QuestionSearchCriteria(
    String questionType,
    String difficulty,
    UUID themeId,
    List<String> tags,
    String searchText,
    Boolean includeDeleted
) {
    public QuestionSearchCriteria {
        if (includeDeleted == null) {
            includeDeleted = false;
        }
    }
    
    public static QuestionSearchCriteria empty() {
        return new QuestionSearchCriteria(null, null, null, null, null, false);
    }
}
