package com.fitnessmanage.system.booking;

import com.fitnessmanage.system.booking.dto.BookSessionRequest;
import com.fitnessmanage.system.booking.dto.BookingResponse;
import com.fitnessmanage.system.booking.dto.ScheduleSlotResponse;
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
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Запись к тренеру")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/available-slots")
    @Operation(summary = "Доступные слоты тренера на дату")
    public ResponseEntity<List<ScheduleSlotResponse>> getAvailableSlots(
            @RequestParam Long trainerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.getAvailableSlots(trainerId, date));
    }

    @PostMapping
    @Operation(summary = "Записаться к тренеру")
    public ResponseEntity<BookingResponse> book(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody BookSessionRequest request) {
        return ResponseEntity.ok(bookingService.bookSession(currentUser.getId(), request));
    }

    @GetMapping("/my")
    @Operation(summary = "Мои записи")
    public ResponseEntity<List<BookingResponse>> getMyBookings(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bookingService.getMyBookings(currentUser.getId()));
    }

    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Отменить свою запись")
    public ResponseEntity<BookingResponse> cancel(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(bookingService.cancelBooking(id, currentUser.getId()));
    }
}