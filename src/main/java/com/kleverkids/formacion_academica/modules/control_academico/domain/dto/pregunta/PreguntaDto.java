package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaDto {
    private Long id;
    private String questionType;
    private String questionText;
    private String difficulty;
    private Integer maxScore;
    private Long themeId;
    private List<MediaDto> media;
    private String hint;
    private String explanation;
    private List<String> tags;
    private Map<String, Object> metadata;
    private Map<String, Object> specificData;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer version;

    /**
     * Los datos específicos del tipo se serializan en la raíz del JSON (options,
     * correct_option_id, items, pairs, ...) en vez de anidados bajo specific_data,
     * para que la respuesta de lectura tenga exactamente la misma forma que el
     * cuerpo que espera CreateQuestionCommand al escribir.
     */
    @JsonAnyGetter
    public Map<String, Object> getSpecificData() {
        return specificData;
    }

}
