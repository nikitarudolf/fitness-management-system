package com.fitnessmanage.system.admin.dto;

import jakarta.validation.constraints.NotNull;

public record AddSubscriptionRequest(
        @NotNull(message = "Укажите тарифный план")
        Long planId
) {}