package com.graduationProject.hrService.repository;

import com.graduationProject.hrService.model.Performance;
import com.graduationProject.hrService.enums.PerformanceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findByEmployeeId(Long employeeId);
    List<Performance> findByOverallRating(PerformanceRating rating);
    List<Performance> findByReviewDateBetween(LocalDate start, LocalDate end);
    List<Performance> findByReviewedBy(String reviewedBy);
}

