package com.fitnessmanage.system.gym.dto;

import java.math.BigDecimal;

public record ClubStatisticsResponse(
    Long totalClients,
    Long totalActiveSubscriptions,
    BigDecimal totalRevenue,
    Integer currentOccupancy
) {}
