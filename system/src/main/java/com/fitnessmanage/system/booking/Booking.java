package com.fitnessmanage.system.booking;

import com.fitnessmanage.system.trainer.TrainerSchedule;
import com.fitnessmanage.system.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private TrainerSchedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;
}
