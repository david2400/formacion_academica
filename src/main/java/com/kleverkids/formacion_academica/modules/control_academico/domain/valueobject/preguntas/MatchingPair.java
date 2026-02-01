package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class MatchingPair extends ValueObject {
    
    private final UUID id;
    private final String leftItem;
    private final String rightItem;
    private final Media leftMedia;
    private final Media rightMedia;
    
    private MatchingPair(UUID id, String leftItem, String rightItem, Media leftMedia, Media rightMedia) {
        this.id = id != null ? id : UUID.randomUUID();
        this.leftItem = Objects.requireNonNull(leftItem, "Left item cannot be null");
        this.rightItem = Objects.requireNonNull(rightItem, "Right item cannot be null");
        this.leftMedia = leftMedia;
        this.rightMedia = rightMedia;
    }
    
    public static MatchingPair create(String leftItem, String rightItem) {
        return new MatchingPair(null, leftItem, rightItem, null, null);
    }
    
    public static MatchingPair create(UUID id, String leftItem, String rightItem, Media leftMedia, Media rightMedia) {
        return new MatchingPair(id, leftItem, rightItem, leftMedia, rightMedia);
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getLeftItem() {
        return leftItem;
    }
    
    public String getRightItem() {
        return rightItem;
    }
    
    public Media getLeftMedia() {
        return leftMedia;
    }
    
    public Media getRightMedia() {
        return rightMedia;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchingPair that = (MatchingPair) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
