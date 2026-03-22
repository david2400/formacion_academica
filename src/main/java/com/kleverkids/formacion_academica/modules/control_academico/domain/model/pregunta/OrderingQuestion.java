package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.OrderingItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;

import java.util.List;
import java.util.Map;

public class OrderingQuestion extends Pregunta {
    
    private List<OrderingItem> items;
    private boolean partialCredit;
    
    public OrderingQuestion() {
        super();
        this.tipoPregunta = TipoPregunta.ORDENAMIENTO;
    }
    
    public OrderingQuestion(Long id, String questionText, Difficulty difficulty, int maxScore,
                             Long themeId, List<Media> media, String hint, String explanation,
                             List<String> tags, Map<String, Object> metadata,
                             List<OrderingItem> items, boolean partialCredit) {
        super(id, questionText, TipoPregunta.ORDENAMIENTO, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.items = items;
        this.partialCredit = partialCredit;
        validar();
    }
    
    public List<OrderingItem> getItems() {
        return items;
    }
    
    public void setItems(List<OrderingItem> items) {
        this.items = items;
    }
    
    public boolean isPartialCredit() {
        return partialCredit;
    }
    
    public void setPartialCredit(boolean partialCredit) {
        this.partialCredit = partialCredit;
    }
    
    @Override
    public void validar() {
        if (items == null || items.size() < 2) {
            throw new InvalidQuestionException("Ordering question must have at least 2 items");
        }
    }
}
