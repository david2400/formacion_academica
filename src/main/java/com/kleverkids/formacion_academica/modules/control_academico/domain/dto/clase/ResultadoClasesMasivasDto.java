package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import java.util.List;

public record ResultadoClasesMasivasDto(int totalSolicitadas,
                                        int totalCreadas,
                                        List<ClaseDto> clasesCreadas) {
}
