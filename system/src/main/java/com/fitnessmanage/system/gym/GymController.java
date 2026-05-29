package com.fitnessmanage.system.gym;

import com.fitnessmanage.system.gym.dto.GymOccupancyResponse;
import com.fitnessmanage.system.gym.dto.VisitResponse;
import com.fitnessmanage.system.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gym")
@RequiredArgsConstructor
@Tag(name = "Gym", description = "Посещения зала")
public class GymController {

    private final GymService gymService;

    @GetMapping("/occupancy")
    @Operation(summary = "Текущая загруженность зала")
    public ResponseEntity<GymOccupancyResponse> getOccupancy() {
        return ResponseEntity.ok(gymService.getOccupancy());
    }

    @PostMapping("/check-in")
    @Operation(summary = "Войти в зал")
    public ResponseEntity<VisitResponse> checkIn(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(gymService.checkIn(currentUser.getId()));
    }

    @PostMapping("/check-out")
    @Operation(summary = "Выйти из зала")
    public ResponseEntity<VisitResponse> checkOut(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(gymService.checkOut(currentUser.getId()));
    }

    @GetMapping("/my-visits")
    @Operation(summary = "Моя история посещений")
    public ResponseEntity<List<VisitResponse>> getMyVisits(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(gymService.getMyVisitHistory(currentUser.getId()));
    }
}