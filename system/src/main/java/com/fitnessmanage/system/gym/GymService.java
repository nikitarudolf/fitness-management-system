package com.fitnessmanage.system.gym;

import com.fitnessmanage.system.common.exception.BusinessException;
import com.fitnessmanage.system.common.exception.ResourceNotFoundException;
import com.fitnessmanage.system.gym.dto.GymOccupancyResponse;
import com.fitnessmanage.system.gym.dto.VisitResponse;
import com.fitnessmanage.system.user.User;
import com.fitnessmanage.system.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GymService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public GymOccupancyResponse getOccupancy() {
        long count = visitRepository.countByCheckOutTimeIsNull();
        return new GymOccupancyResponse((int) count);
    }

    @Transactional
    public VisitResponse checkIn(Long userId) {
        if (visitRepository.findByUserIdAndCheckOutTimeIsNull(userId).isPresent()) {
            throw new BusinessException("Вы уже находитесь в зале. Сначала выйдите.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        Visit visit = new Visit();
        visit.setUser(user);
        visit.setCheckInTime(LocalDateTime.now());

        visitRepository.save(visit);
        return toResponse(visit);
    }

    @Transactional
    public VisitResponse checkOut(Long userId) {
        Visit visit = visitRepository.findByUserIdAndCheckOutTimeIsNull(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Активного посещения не найдено. Сначала войдите."));

        visit.setCheckOutTime(LocalDateTime.now());
        return toResponse(visit);
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> getMyVisitHistory(Long userId) {
        return visitRepository.findByUserIdOrderByCheckInTimeDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    private VisitResponse toResponse(Visit visit) {
        return new VisitResponse(
                visit.getId(),
                visit.getCheckInTime(),
                visit.getCheckOutTime()
        );
    }
}