package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import com.kleverkids.formacion_academica.shared.common.domain.dto.AuditInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GrupoDto extends AuditInfoDto {

    private UUID id;
    private String codigo;
    private String nombre;
    private UUID gradoId;
    private Integer capacidadMaxima;
    private UUID tutorId;
    private UUID aulaId;
    private boolean activo;
    private AuditInfoDto audit;

}
