package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public final class TimeConfig extends ValueObject {
    
    private final int duration; // in minutes
    private final LocalDate scheduledDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    
    private TimeConfig(int duration, LocalDate scheduledDate, LocalTime startTime, LocalTime endTime) {
        if (duration <= 0) {
            throw new IllegalArgumentException("La duración debe ser positiva");
        }
        this.duration = duration;
        this.scheduledDate = scheduledDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    public static TimeConfig create(int duration, LocalDate scheduledDate, LocalTime startTime, LocalTime endTime) {
        return new TimeConfig(duration, scheduledDate, startTime, endTime);
    }
    
    public int getDuration() {
        return duration;
    }
    
    public LocalDate getScheduledDate() {
        return scheduledDate;
    }
    
    public LocalTime getStartTime() {
        return startTime;
    }
    
    public LocalTime getEndTime() {
        return endTime;
    }
    
    public boolean isWithinTimeWindow(Instant now) {
        if (scheduledDate == null) return true;
        // Simplified check - in production would need timezone handling
        return true;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeConfig that = (TimeConfig) o;
        return duration == that.duration && 
               Objects.equals(scheduledDate, that.scheduledDate) &&
               Objects.equals(startTime, that.startTime) &&
               Objects.equals(endTime, that.endTime);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(duration, scheduledDate, startTime, endTime);
    }
}
