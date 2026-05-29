package com.fitnessmanage.system.admin;

import com.fitnessmanage.system.admin.dto.AddSubscriptionRequest;
import com.fitnessmanage.system.admin.dto.ClubStatisticsResponse;
import com.fitnessmanage.system.admin.dto.RegisterTrainerRequest;
import com.fitnessmanage.system.common.exception.BusinessException;
import com.fitnessmanage.system.common.exception.ResourceNotFoundException;
import com.fitnessmanage.system.gym.VisitRepository;
import com.fitnessmanage.system.subscription.SubscriptionPlan;
import com.fitnessmanage.system.subscription.SubscriptionPlanRepository;
import com.fitnessmanage.system.subscription.UserSubscription;
import com.fitnessmanage.system.subscription.UserSubscriptionRepository;
import com.fitnessmanage.system.trainer.TrainerProfile;
import com.fitnessmanage.system.trainer.TrainerProfileRepository;
import com.fitnessmanage.system.user.Role;
import com.fitnessmanage.system.user.User;
import com.fitnessmanage.system.user.UserRepository;
import com.fitnessmanage.system.user.dto.UserResponse;
import com.fitnessmanage.system.subscription.dto.UserSubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final TrainerProfileRepository trainerProfileRepository;
    private final SubscriptionPlanRepository planRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;
    private final VisitRepository visitRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ClubStatisticsResponse getClubStatistics() {
        long totalClients = userRepository.countByRole(Role.USER);
        long activeSubscriptions = userSubscriptionRepository.countActiveSubscriptions();
        BigDecimal totalRevenue = userSubscriptionRepository.calculateTotalRevenue();
        int currentOccupancy = (int) visitRepository.countByCheckOutTimeIsNull();

        return new ClubStatisticsResponse(
                totalClients,
                activeSubscriptions,
                totalRevenue != null ? totalRevenue : BigDecimal.ZERO,
                currentOccupancy
        );
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllClients() {
        return userRepository.findAllByRole(Role.USER).stream()
                .map(this::toUserResponse)
                .toList();
    }

    @Transactional
    public UserResponse registerTrainer(RegisterTrainerRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRole(Role.TRAINER);
        userRepository.save(user);

        TrainerProfile profile = new TrainerProfile();
        profile.setUser(user);
        profile.setSpecialty(request.specialty());
        profile.setBio(request.bio());
        trainerProfileRepository.save(profile);

        return toUserResponse(user);
    }

    @Transactional
    public UserSubscriptionResponse addSubscriptionToClient(Long clientId, AddSubscriptionRequest request) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент не найден"));

        SubscriptionPlan plan = planRepository.findById(request.planId())
                .orElseThrow(() -> new ResourceNotFoundException("Тарифный план не найден"));

        UserSubscription subscription = new UserSubscription();
        subscription.setUser(client);
        subscription.setPlan(plan);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(plan.getDurationDays()));
        subscription.setIsActive(true);

        userSubscriptionRepository.save(subscription);

        return new UserSubscriptionResponse(
                subscription.getId(),
                plan.getName(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getIsActive()
        );
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().name()
        );
    }
}