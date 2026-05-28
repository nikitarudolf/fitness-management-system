package com.fitnessmanage.system.user.dto;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone,
    String role
) {}
