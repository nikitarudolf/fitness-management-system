package com.fitnessmanage.system.trainer.dto;

public record TrainerResponse(
        Long id,
        String fullName,
        String specialty,
        String bio
) {}
