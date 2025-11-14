package com.graduationProject.hrService.repository;

import com.graduationProject.hrService.model.Training;
import com.graduationProject.hrService.enums.TrainingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findByStatus(TrainingStatus status);
    List<Training> findByStartDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Training> findByTrainerName(String trainerName);
}

