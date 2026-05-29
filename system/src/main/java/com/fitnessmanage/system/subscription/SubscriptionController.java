package com.fitnessmanage.system.subscription;

import com.fitnessmanage.system.subscription.dto.PurchaseSubscriptionRequest;
import com.fitnessmanage.system.subscription.dto.SubscriptionPlanResponse;
import com.fitnessmanage.system.subscription.dto.UserSubscriptionResponse;
import com.fitnessmanage.system.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Управление абонементами")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    @Operation(summary = "Список всех тарифных планов")
    public ResponseEntity<List<SubscriptionPlanResponse>> getAllPlans() {
        return ResponseEntity.ok(subscriptionService.getAllPlans());
    }

    @PostMapping("/purchase")
    @Operation(summary = "Купить абонемент")
    public ResponseEntity<UserSubscriptionResponse> purchase(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody PurchaseSubscriptionRequest request) {
        return ResponseEntity.ok(
                subscriptionService.purchaseSubscription(currentUser.getId(), request)
        );
    }

    @GetMapping("/my")
    @Operation(summary = "Мой текущий активный абонемент")
    public ResponseEntity<UserSubscriptionResponse> getMySubscription(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(
                subscriptionService.getMyActiveSubscription(currentUser.getId())
        );
    }
}