package com.fitnessmanage.system.booking.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateScheduleSlotRequest(
    LocalDate workingDate,
    LocalTime startTime,
    LocalTime endTime
) {}
