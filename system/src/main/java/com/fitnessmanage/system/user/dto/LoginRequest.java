package com.fitnessmanage.system.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email-адреса")
    String email,

    @NotBlank(message = "Пароль обязателен")
    String password
) {}
