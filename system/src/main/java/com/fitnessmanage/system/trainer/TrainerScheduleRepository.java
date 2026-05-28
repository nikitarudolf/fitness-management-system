package com.fitnessmanage.system.trainer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainerScheduleRepository extends JpaRepository<TrainerSchedule, Long> {
    
    List<TrainerSchedule> findByTrainerIdAndWorkingDate(Long trainerId, LocalDate workingDate);
}
