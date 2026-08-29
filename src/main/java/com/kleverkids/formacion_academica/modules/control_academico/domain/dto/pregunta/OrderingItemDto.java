package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderingItemDto {
    private Long id;
    private String text;
    private Integer correctPosition;
    private MediaDto media;

}
