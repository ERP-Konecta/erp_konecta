package com.graduationProject.hrService.controller;

import com.graduationProject.hrService.dto.ApiResponse;
import com.graduationProject.hrService.dto.AttendanceDTO;
import com.graduationProject.hrService.enums.AttendanceStatus;
import com.graduationProject.hrService.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hr/attendances")
@RequiredArgsConstructor
@Tag(name = "Attendance Management", description = "APIs for managing attendance")
@PreAuthorize("hasAuthority('HR')")
public class AttendanceController {
    
    private final AttendanceService attendanceService;
    
    @Operation(summary = "Create a new attendance record")
    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceDTO>> createAttendance(@Valid @RequestBody AttendanceDTO dto) {
        AttendanceDTO attendance = attendanceService.createAttendance(dto);
        ApiResponse<AttendanceDTO> response = new ApiResponse<>(
            "success",
            "Attendance record created successfully",
            attendance
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all attendance records")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAllAttendances() {
        List<AttendanceDTO> attendances = attendanceService.getAllAttendances();
        ApiResponse<List<AttendanceDTO>> response = new ApiResponse<>(
            "success",
            "Attendance records retrieved successfully",
            attendances
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get attendance record by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceDTO>> getAttendanceById(@PathVariable Long id) {
        AttendanceDTO attendance = attendanceService.getAttendanceById(id);
        ApiResponse<AttendanceDTO> response = new ApiResponse<>(
            "success",
            "Attendance record retrieved successfully",
            attendance
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update attendance record")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AttendanceDTO>> updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody AttendanceDTO dto) {
        AttendanceDTO attendance = attendanceService.updateAttendance(id, dto);
        ApiResponse<AttendanceDTO> response = new ApiResponse<>(
            "success",
            "Attendance record updated successfully",
            attendance
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete attendance record")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Attendance record deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get attendance records by employee ID")
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAttendancesByEmployeeId(
            @PathVariable Long employeeId) {
        List<AttendanceDTO> attendances = attendanceService.getAttendancesByEmployeeId(employeeId);
        ApiResponse<List<AttendanceDTO>> response = new ApiResponse<>(
            "success",
            "Attendance records retrieved successfully",
            attendances
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get attendance records by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AttendanceDTO>>> getAttendancesByStatus(
            @PathVariable AttendanceStatus status) {
        List<AttendanceDTO> attendances = attendanceService.getAttendancesByStatus(status);
        ApiResponse<List<AttendanceDTO>> response = new ApiResponse<>(
            "success",
            "Attendance records retrieved successfully",
            attendances
        );
        return ResponseEntity.ok(response);
    }
}

