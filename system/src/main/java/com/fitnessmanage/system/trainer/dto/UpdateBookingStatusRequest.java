package com.fitnessmanage.system.trainer.dto;

import com.fitnessmanage.system.booking.BookingStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateBookingStatusRequest(
        @NotNull(message = "Статус обязателен")
        BookingStatus status
) {}