package com.fitnessmanage.system.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleSlotResponse(
    Long id,
    Long trainerId,
    String trainerFullName,
    LocalDate workingDate,
    LocalTime startTime,
    LocalTime endTime
) {}
