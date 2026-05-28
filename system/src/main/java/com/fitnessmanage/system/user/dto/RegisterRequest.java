package com.fitnessmanage.system.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "Имя не может быть пустым")
    String firstName,

    @NotBlank(message = "Фамилия не может быть пустой")
    String lastName,

    @NotBlank(message = "Email обязателен")
    @Email(message = "Неверный формат email-адреса")
    String email,

    @NotBlank(message = "Номер телефона обязателен")
    String phone,

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    String password
) {}
