package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdenItemDto {

    @NotNull
    private Long itemId;

    @NotNull
    private Integer orden;
}
