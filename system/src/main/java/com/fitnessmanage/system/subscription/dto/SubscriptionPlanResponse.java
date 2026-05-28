package com.fitnessmanage.system.subscription.dto;

import java.math.BigDecimal;

public record SubscriptionPlanResponse(
    Long id,
    String name,
    BigDecimal price,
    Integer durationDays,
    Boolean includesTrainerAccess
) {}
