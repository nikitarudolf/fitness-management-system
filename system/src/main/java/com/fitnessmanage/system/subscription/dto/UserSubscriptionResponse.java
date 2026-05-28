package com.fitnessmanage.system.subscription.dto;

import java.time.LocalDate;

public record UserSubscriptionResponse(
    Long id,
    String planName,
    LocalDate startDate,
    LocalDate endDate,
    Boolean isActive
) {}
