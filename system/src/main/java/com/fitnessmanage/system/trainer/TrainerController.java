package com.fitnessmanage.system.trainer;

import com.fitnessmanage.system.booking.dto.BookingResponse;
import com.fitnessmanage.system.booking.dto.CreateScheduleSlotRequest;
import com.fitnessmanage.system.booking.dto.ScheduleSlotResponse;
import com.fitnessmanage.system.trainer.dto.TrainerResponse;
import com.fitnessmanage.system.trainer.dto.UpdateBookingStatusRequest;
import com.fitnessmanage.system.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trainers")
@RequiredArgsConstructor
@Tag(name = "Trainers", description = "Тренеры и расписание")
public class TrainerController {

    private final TrainerService trainerService;

    @GetMapping
    @Operation(summary = "Список всех тренеров")
    public ResponseEntity<List<TrainerResponse>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/me/schedule")
    @Operation(summary = "Моё расписание на дату")
    public ResponseEntity<List<BookingResponse>> getMySchedule(
            @AuthenticationPrincipal User currentUser,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(trainerService.getMySchedule(currentUser.getId(), date));
    }

    @PostMapping("/me/schedule")
    @Operation(summary = "Создать слот в расписании")
    public ResponseEntity<ScheduleSlotResponse> createSlot(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody CreateScheduleSlotRequest request) {
        return ResponseEntity.ok(trainerService.createSlot(currentUser.getId(), request));
    }

    @PatchMapping("/me/bookings/{bookingId}/status")
    @Operation(summary = "Изменить статус записи клиента")
    public ResponseEntity<BookingResponse> updateBookingStatus(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateBookingStatusRequest request) {
        return ResponseEntity.ok(
                trainerService.updateBookingStatus(bookingId, currentUser.getId(), request.status())
        );
    }
}