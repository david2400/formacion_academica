package com.kleverkids.formacion_academica.modules.questions.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionType {
    MULTIPLE_CHOICE_SINGLE("multiple_choice_single"),
    MULTIPLE_CHOICE_MULTI("multiple_choice_multi"),
    TRUE_FALSE("true_false"),
    OPEN_SHORT("open_short"),
    OPEN_LONG("open_long"),
    NUMERIC("numeric"),
    SCALE("scale"),
    ORDERING("ordering"),
    MATCHING("matching");
    
    private final String value;
    
    QuestionType(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public static QuestionType fromValue(String value) {
        for (QuestionType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown question type: " + value);
    }
}
