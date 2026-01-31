package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.time.LocalDate;
import java.time.LocalTime;

public record TimeConfigDto(
    int duration,
    LocalDate scheduledDate,
    LocalTime startTime,
    LocalTime endTime
) {}
