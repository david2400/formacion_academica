package com.kleverkids.formacion_academica.modules.questions.domain.valueobject;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class Option extends ValueObject {
    
    private final UUID id;
    private final String text;
    private final Media media;
    private final boolean isCorrect;
    
    private Option(UUID id, String text, Media media, boolean isCorrect) {
        this.id = id != null ? id : UUID.randomUUID();
        this.text = Objects.requireNonNull(text, "Option text cannot be null");
        this.media = media;
        this.isCorrect = isCorrect;
    }
    
    public static Option create(String text, boolean isCorrect) {
        return new Option(null, text, null, isCorrect);
    }
    
    public static Option create(UUID id, String text, Media media, boolean isCorrect) {
        return new Option(id, text, media, isCorrect);
    }
    
    public UUID getId() {
        return id;
    }
    
    public String getText() {
        return text;
    }
    
    public Media getMedia() {
        return media;
    }
    
    public boolean isCorrect() {
        return isCorrect;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(id, option.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
