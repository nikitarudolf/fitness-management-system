package com.fitnessmanage.system.user.dto;

public record AuthResponse(
    String token,
    String email,
    String role
) {}
