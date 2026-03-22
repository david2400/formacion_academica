package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;

import java.util.List;

public record ResultadoClasesMasivasDto(int totalSolicitadas,
                                        int totalCreadas,
                                        List<Clase> clasesCreadas) {
}
