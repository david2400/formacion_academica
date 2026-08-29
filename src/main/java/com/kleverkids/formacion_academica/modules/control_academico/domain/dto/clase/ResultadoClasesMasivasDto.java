package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoClasesMasivasDto {
    private int totalSolicitadas;
    private int totalCreadas;
    private List<Clase> clasesCreadas;

}
