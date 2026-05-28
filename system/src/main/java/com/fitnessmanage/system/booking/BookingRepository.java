package com.fitnessmanage.system.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    
    List<Booking> findByClientId(Long clientId);

    @Query("SELECT b FROM Booking b " +
           "WHERE b.schedule.trainer.id = :trainerId " +
           "AND b.schedule.workingDate = :date")
    List<Booking> findBookingsForTrainerByDate(@Param("trainerId") Long trainerId, @Param("date") LocalDate date);

    boolean existsByScheduleIdAndStatusIn(Long scheduleId, List<BookingStatus> statuses);
}
