package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class OrderingItem extends ValueObject {
    
    private final UUID id;
    private final String text;
    private final int correctPosition;
    private final Media media;
    
    private OrderingItem(UUID id, String text, int correctPosition, Media media) {
        this.id = id != null ? id : UUID.randomUUID();
        this.text = Objects.requireNonNull(text, "Item text cannot be null");
        if (correctPosition < 0) {
            throw new IllegalArgumentException("Correct position must be >= 0");
        }
        this.correctPosition = correctPosition;
        this.media = media;
    }
    
    public static OrderingItem create(String text, int correctPosition) {
        return new OrderingItem(null, text, correctPosition, null);
    }
    
    public static OrderingItem create(UUID id, String text, int correctPosition, Media media) {
        return new OrderingItem(id, text, correctPosition, media);
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getText() {
        return text;
    }
    
    public int getCorrectPosition() {
        return correctPosition;
    }
    
    public Media getMedia() {
        return media;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderingItem that = (OrderingItem) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
