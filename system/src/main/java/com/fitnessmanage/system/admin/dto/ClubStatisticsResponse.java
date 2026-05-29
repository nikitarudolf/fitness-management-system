package com.fitnessmanage.system.admin.dto;

import java.math.BigDecimal;

public record ClubStatisticsResponse(
        Long totalClients,
        Long activeSubscriptions,
        BigDecimal totalRevenue,
        Integer currentOccupancy
) {}