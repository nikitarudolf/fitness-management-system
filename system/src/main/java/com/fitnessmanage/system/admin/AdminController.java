package com.fitnessmanage.system.admin;

import com.fitnessmanage.system.admin.dto.AddSubscriptionRequest;
import com.fitnessmanage.system.admin.dto.ClubStatisticsResponse;
import com.fitnessmanage.system.admin.dto.RegisterTrainerRequest;
import com.fitnessmanage.system.subscription.dto.UserSubscriptionResponse;
import com.fitnessmanage.system.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Административные функции")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/stats")
    @Operation(summary = "Статистика клуба")
    public ResponseEntity<ClubStatisticsResponse> getStats() {
        return ResponseEntity.ok(adminService.getClubStatistics());
    }

    @GetMapping("/clients")
    @Operation(summary = "Список всех клиентов")
    public ResponseEntity<List<UserResponse>> getAllClients() {
        return ResponseEntity.ok(adminService.getAllClients());
    }

    @PostMapping("/trainers")
    @Operation(summary = "Зарегистрировать тренера")
    public ResponseEntity<UserResponse> registerTrainer(
            @Valid @RequestBody RegisterTrainerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(adminService.registerTrainer(request));
    }

    @PostMapping("/clients/{clientId}/subscriptions")
    @Operation(summary = "Добавить абонемент клиенту")
    public ResponseEntity<UserSubscriptionResponse> addSubscription(
            @PathVariable Long clientId,
            @Valid @RequestBody AddSubscriptionRequest request) {
        return ResponseEntity.ok(adminService.addSubscriptionToClient(clientId, request));
    }
}