package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

public record ExamSearchCriteria(
    String status,
    String subject,
    String gradeLevel,
    String searchText,
    Boolean includeDeleted
) {
    public ExamSearchCriteria {
        if (includeDeleted == null) {
            includeDeleted = false;
        }
    }
}
