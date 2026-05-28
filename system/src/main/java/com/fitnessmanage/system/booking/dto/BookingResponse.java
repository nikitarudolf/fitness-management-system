package com.fitnessmanage.system.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingResponse(
    Long id,
    Long scheduleId,
    String trainerFullName,
    LocalDate date,
    LocalTime startTime,
    String status
) {}
