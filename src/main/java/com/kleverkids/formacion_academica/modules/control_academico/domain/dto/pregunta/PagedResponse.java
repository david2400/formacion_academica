package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T> {
    private List<T> content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean first;
    private Boolean last;
    private Boolean empty;

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
