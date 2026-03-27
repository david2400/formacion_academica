package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.nivel_academico;

import java.util.List;

public record NivelEducativoEstadisticasDto(
    Long totalNiveles,
    Long nivelesActivos,
    Long nivelesInactivos,
    Long totalPreescolar,
    Long totalBasica,
    Long totalMedia,
    Long totalSuperior,
    List<NivelEducativoResumenDto> porCategoria,
    List<NivelEducativoResumenDto> nivelesJerarquicos
) {
    
    public record NivelEducativoResumenDto(
        String categoria,
        Long cantidad,
        Double porcentaje
    ) {
        public NivelEducativoResumenDto(String categoria, Long cantidad) {
            this(categoria, cantidad, 0.0);
        }
    }
}
