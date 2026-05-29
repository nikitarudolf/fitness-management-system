package com.fitnessmanage.system.gym.dto;

import java.time.LocalDateTime;

public record VisitResponse(
        Long id,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime
) {}