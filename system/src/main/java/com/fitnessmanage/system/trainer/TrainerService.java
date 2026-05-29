package com.fitnessmanage.system.trainer;

import com.fitnessmanage.system.booking.Booking;
import com.fitnessmanage.system.booking.BookingRepository;
import com.fitnessmanage.system.booking.BookingStatus;
import com.fitnessmanage.system.booking.dto.BookingResponse;
import com.fitnessmanage.system.booking.dto.CreateScheduleSlotRequest;
import com.fitnessmanage.system.booking.dto.ScheduleSlotResponse;
import com.fitnessmanage.system.common.exception.BusinessException;
import com.fitnessmanage.system.common.exception.ResourceNotFoundException;
import com.fitnessmanage.system.trainer.dto.TrainerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {

    private final TrainerProfileRepository trainerProfileRepository;
    private final TrainerScheduleRepository scheduleRepository;
    private final BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public List<TrainerResponse> getAllTrainers() {
        return trainerProfileRepository.findAll().stream()
                .map(this::toTrainerResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> getMySchedule(Long userId, LocalDate date) {
        TrainerProfile trainer = trainerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Профиль тренера не найден"));

        return bookingRepository.findBookingsForTrainerByDate(trainer.getId(), date).stream()
                .map(this::toBookingResponse)
                .toList();
    }

    @Transactional
    public ScheduleSlotResponse createSlot(Long userId, CreateScheduleSlotRequest request) {
        TrainerProfile trainer = trainerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Профиль тренера не найден"));

        if (request.startTime().isAfter(request.endTime()) ||
                request.startTime().equals(request.endTime())) {
            throw new BusinessException("Время начала должно быть раньше времени окончания");
        }

        TrainerSchedule slot = new TrainerSchedule();
        slot.setTrainer(trainer);
        slot.setWorkingDate(request.workingDate());
        slot.setStartTime(request.startTime());
        slot.setEndTime(request.endTime());

        scheduleRepository.save(slot);
        return toSlotResponse(slot, trainer);
    }

    @Transactional
    public BookingResponse updateBookingStatus(Long bookingId, Long userId, BookingStatus newStatus) {
        TrainerProfile trainer = trainerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Профиль тренера не найден"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Запись не найдена"));

        if (!booking.getSchedule().getTrainer().getId().equals(trainer.getId())) {
            throw new BusinessException("Вы можете управлять только своими записями");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BusinessException("Нельзя изменить уже отменённую запись");
        }

        booking.setStatus(newStatus);
        return toBookingResponse(booking);
    }

    private TrainerResponse toTrainerResponse(TrainerProfile trainer) {
        return new TrainerResponse(
                trainer.getId(),
                trainer.getUser().getFirstName() + " " + trainer.getUser().getLastName(),
                trainer.getSpecialty(),
                trainer.getBio()
        );
    }

    private BookingResponse toBookingResponse(Booking booking) {
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

    private ScheduleSlotResponse toSlotResponse(TrainerSchedule slot, TrainerProfile trainer) {
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