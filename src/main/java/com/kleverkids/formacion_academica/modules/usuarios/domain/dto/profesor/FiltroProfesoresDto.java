package com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Filtros del listado. {@code texto} busca en nombre, usuario y número de documento. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroProfesoresDto {
    private Long empresaId;
    private String texto;
    private Boolean activo;
}
