package com.fitnessmanage.system.subscription;

import com.fitnessmanage.system.common.exception.BusinessException;
import com.fitnessmanage.system.common.exception.ResourceNotFoundException;
import com.fitnessmanage.system.subscription.dto.PurchaseSubscriptionRequest;
import com.fitnessmanage.system.subscription.dto.SubscriptionPlanResponse;
import com.fitnessmanage.system.subscription.dto.UserSubscriptionResponse;
import com.fitnessmanage.system.user.User;
import com.fitnessmanage.system.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionPlanRepository planRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<SubscriptionPlanResponse> getAllPlans() {
        return planRepository.findAll().stream()
                .map(plan -> new SubscriptionPlanResponse(
                        plan.getId(),
                        plan.getName(),
                        plan.getPrice(),
                        plan.getDurationDays(),
                        plan.getIncludesTrainerAccess()
                ))
                .toList();
    }

    @Transactional
    public UserSubscriptionResponse purchaseSubscription(Long userId, PurchaseSubscriptionRequest request) {
        if (userSubscriptionRepository.findActiveSubscription(userId).isPresent()) {
            throw new BusinessException("У вас уже есть активный абонемент. Дождитесь его окончания.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        SubscriptionPlan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ResourceNotFoundException("Тарифный план не найден"));

        UserSubscription subscription = new UserSubscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(plan.getDurationDays()));
        subscription.setIsActive(true);

        userSubscriptionRepository.save(subscription);
        return toResponse(subscription);
    }

    @Transactional(readOnly = true)
    public UserSubscriptionResponse getMyActiveSubscription(Long userId) {
        return userSubscriptionRepository.findActiveSubscription(userId)
                .map(this::toResponse)
                .orElseThrow(() -> new BusinessException("У вас нет активного абонемента"));
    }

    @Transactional(readOnly = true)
    public boolean hasActiveSubscriptionWithTrainer(Long userId) {
        return userSubscriptionRepository.findActiveSubscription(userId)
                .map(sub -> sub.getPlan().getIncludesTrainerAccess())
                .orElse(false);
    }

    private UserSubscriptionResponse toResponse(UserSubscription sub) {
        return new UserSubscriptionResponse(
                sub.getId(),
                sub.getPlan().getName(),
                sub.getStartDate(),
                sub.getEndDate(),
                sub.getIsActive()
        );
    }
}