package com.graduationProject.hrService.repository;

import com.graduationProject.hrService.model.Attendance;
import com.graduationProject.hrService.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByEmployeeId(Long employeeId);
    List<Attendance> findByStatus(AttendanceStatus status);
    List<Attendance> findByAttendanceDateBetween(LocalDate start, LocalDate end);
    List<Attendance> findByEmployeeIdAndAttendanceDateBetween(Long employeeId, LocalDate start, LocalDate end);
}

