package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.EstadoLearningSequence;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroLearningSequenceDto {

    private String texto;
    private EstadoLearningSequence estado;

    public boolean estaVacio() {
        return (texto == null || texto.isBlank()) && estado == null;
    }
}
