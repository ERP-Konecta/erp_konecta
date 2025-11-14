package com.graduationProject.hrService.service;

import com.graduationProject.hrService.dto.AttendanceDTO;
import com.graduationProject.hrService.enums.AttendanceStatus;
import com.graduationProject.hrService.exception.ResourceNotFoundException;
import com.graduationProject.hrService.model.Attendance;
import com.graduationProject.hrService.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    
    @Transactional
    public AttendanceDTO createAttendance(AttendanceDTO dto) {
        String recordedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Calculate working hours if check-in and check-out times are provided
        Integer workingHours = null;
        if (dto.getCheckInTime() != null && dto.getCheckOutTime() != null) {
            Duration duration = Duration.between(dto.getCheckInTime(), dto.getCheckOutTime());
            workingHours = (int) duration.toHours();
        }
        
        Attendance attendance = Attendance.builder()
            .employeeId(dto.getEmployeeId())
            .employeeName(dto.getEmployeeName())
            .employeeEmail(dto.getEmployeeEmail())
            .attendanceDate(dto.getAttendanceDate())
            .status(dto.getStatus())
            .checkInTime(dto.getCheckInTime())
            .checkOutTime(dto.getCheckOutTime())
            .workingHours(workingHours != null ? workingHours : dto.getWorkingHours())
            .overtimeHours(dto.getOvertimeHours())
            .notes(dto.getNotes())
            .recordedBy(recordedBy)
            .createdAt(LocalDate.now())
            .build();
        
        return toDTO(attendanceRepository.save(attendance));
    }
    
    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public AttendanceDTO getAttendanceById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
        return toDTO(attendance);
    }
    
    @Transactional
    public AttendanceDTO updateAttendance(Long id, AttendanceDTO dto) {
        Attendance attendance = attendanceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with id: " + id));
        
        // Calculate working hours if check-in and check-out times are provided
        Integer workingHours = null;
        if (dto.getCheckInTime() != null && dto.getCheckOutTime() != null) {
            Duration duration = Duration.between(dto.getCheckInTime(), dto.getCheckOutTime());
            workingHours = (int) duration.toHours();
        }
        
        attendance.setEmployeeName(dto.getEmployeeName());
        attendance.setEmployeeEmail(dto.getEmployeeEmail());
        attendance.setAttendanceDate(dto.getAttendanceDate());
        attendance.setStatus(dto.getStatus());
        attendance.setCheckInTime(dto.getCheckInTime());
        attendance.setCheckOutTime(dto.getCheckOutTime());
        attendance.setWorkingHours(workingHours != null ? workingHours : dto.getWorkingHours());
        attendance.setOvertimeHours(dto.getOvertimeHours());
        attendance.setNotes(dto.getNotes());
        attendance.setUpdatedAt(LocalDate.now());
        
        return toDTO(attendanceRepository.save(attendance));
    }
    
    @Transactional
    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Attendance not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }
    
    public List<AttendanceDTO> getAttendancesByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<AttendanceDTO> getAttendancesByStatus(AttendanceStatus status) {
        return attendanceRepository.findByStatus(status).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private AttendanceDTO toDTO(Attendance attendance) {
        return AttendanceDTO.builder()
            .id(attendance.getId())
            .employeeId(attendance.getEmployeeId())
            .employeeName(attendance.getEmployeeName())
            .employeeEmail(attendance.getEmployeeEmail())
            .attendanceDate(attendance.getAttendanceDate())
            .status(attendance.getStatus())
            .checkInTime(attendance.getCheckInTime())
            .checkOutTime(attendance.getCheckOutTime())
            .workingHours(attendance.getWorkingHours())
            .overtimeHours(attendance.getOvertimeHours())
            .notes(attendance.getNotes())
            .build();
    }
}

