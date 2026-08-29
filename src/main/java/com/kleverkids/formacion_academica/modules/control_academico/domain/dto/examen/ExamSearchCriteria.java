package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
public class ExamSearchCriteria {
    private String status;
    private String subject;
    private String gradeLevel;
    private String searchText;
    private Boolean includeDeleted;

public ExamSearchCriteria(String status, String subject, String gradeLevel, String searchText, Boolean includeDeleted) {
        if (includeDeleted == null) {
            includeDeleted = false;
        this.status = status;
        this.subject = subject;
        this.gradeLevel = gradeLevel;
        this.searchText = searchText;
        this.includeDeleted = includeDeleted;
    }
    }

}
