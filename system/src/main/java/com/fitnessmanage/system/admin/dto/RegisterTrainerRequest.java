package com.fitnessmanage.system.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterTrainerRequest(
        @NotBlank(message = "Имя обязательно")
        String firstName,

        @NotBlank(message = "Фамилия обязательна")
        String lastName,

        @NotBlank @Email(message = "Неверный формат email")
        String email,

        String phone,

        @NotBlank @Size(min = 6, message = "Пароль минимум 6 символов")
        String password,

        String specialty,
        String bio
) {}