package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import com.kleverkids.formacion_academica.shared.common.domain.dto.AuditInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDto extends AuditInfoDto {

    private Long id;
    private String codigo;
    private String nombre;
    private Long gradoId;
    private Integer capacidadMaxima;
    private Long tutorId;
    private Long aulaId;
    private boolean activo;

}
