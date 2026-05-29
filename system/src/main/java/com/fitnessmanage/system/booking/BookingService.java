package com.fitnessmanage.system.booking;

import com.fitnessmanage.system.booking.dto.BookSessionRequest;
import com.fitnessmanage.system.booking.dto.BookingResponse;
import com.fitnessmanage.system.booking.dto.ScheduleSlotResponse;
import com.fitnessmanage.system.common.exception.BusinessException;
import com.fitnessmanage.system.common.exception.ResourceNotFoundException;
import com.fitnessmanage.system.subscription.SubscriptionService;
import com.fitnessmanage.system.trainer.TrainerProfile;
import com.fitnessmanage.system.trainer.TrainerProfileRepository;
import com.fitnessmanage.system.trainer.TrainerSchedule;
import com.fitnessmanage.system.trainer.TrainerScheduleRepository;
import com.fitnessmanage.system.user.User;
import com.fitnessmanage.system.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TrainerScheduleRepository scheduleRepository;
    private final TrainerProfileRepository trainerProfileRepository;
    private final UserRepository userRepository;
    private final SubscriptionService subscriptionService;

    @Transactional(readOnly = true)
    public List<ScheduleSlotResponse> getAvailableSlots(Long trainerId, LocalDate date) {
        TrainerProfile trainer = trainerProfileRepository.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Тренер не найден"));

        return scheduleRepository.findByTrainerIdAndWorkingDate(trainerId, date).stream()
                .filter(slot -> !bookingRepository.existsByScheduleIdAndStatusIn(
                        slot.getId(), List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
                ))
                .map(this::toSlotResponse)
                .toList();
    }

    @Transactional
    public BookingResponse bookSession(Long userId, BookSessionRequest request) {
        if (!subscriptionService.hasActiveSubscriptionWithTrainer(userId)) {
            throw new BusinessException(
                    "Для записи к тренеру нужен абонемент с опцией 'С тренером'");
        }

        TrainerSchedule slot = scheduleRepository.findById(request.scheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Слот расписания не найден"));

        boolean slotTaken = bookingRepository.existsByScheduleIdAndStatusIn(
                slot.getId(), List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED));
        if (slotTaken) {
            throw new BusinessException("Этот слот уже занят");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден"));

        Booking booking = new Booking();
        booking.setClient(user);
        booking.setSchedule(slot);
        booking.setStatus(BookingStatus.PENDING);

        bookingRepository.save(booking);
        return toResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBookings(Long userId) {
        return bookingRepository.findByClientId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public BookingResponse cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));

        if (!booking.getClient().getId().equals(userId)) {
            throw new BusinessException("Вы можете отменять только свои записи");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BusinessException("Запись уже отменена");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return toResponse(booking);
    }

    private BookingResponse toResponse(Booking booking) {
        TrainerSchedule schedule = booking.getSchedule();
        TrainerProfile trainer = schedule.getTrainer();
        String trainerName = trainer.getUser().getFirstName() + " " + trainer.getUser().getLastName();

        return new BookingResponse(
                booking.getId(),
                schedule.getId(),
                trainerName,
                schedule.getWorkingDate(),
                schedule.getStartTime(),
                booking.getStatus().name()
        );
    }

    private ScheduleSlotResponse toSlotResponse(TrainerSchedule slot) {
        TrainerProfile trainer = slot.getTrainer();
        String trainerName = trainer.getUser().getFirstName() + " " + trainer.getUser().getLastName();

        return new ScheduleSlotResponse(
                slot.getId(),
                trainer.getId(),
                trainerName,
                slot.getWorkingDate(),
                slot.getStartTime(),
                slot.getEndTime()
        );
    }
}