package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;

public record PagedResponse<T>(
    List<T> content,
    Integer pageNumber,
    Integer pageSize,
    Long totalElements,
    Integer totalPages,
    Boolean first,
    Boolean last,
    Boolean empty
) {
    public static <T> PagedResponse<T> of(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Long totalElements
    ) {
        Integer totalPages = (int) Math.ceil((double) totalElements / pageSize);
        return new PagedResponse<>(
            content,
            pageNumber,
            pageSize,
            totalElements,
            totalPages,
            pageNumber == 0,
            pageNumber >= totalPages - 1,
            content.isEmpty()
        );
    }
}
