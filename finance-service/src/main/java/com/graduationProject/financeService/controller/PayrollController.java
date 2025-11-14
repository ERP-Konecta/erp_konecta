package com.graduationProject.financeService.controller;

import com.graduationProject.financeService.dto.ApiResponse;
import com.graduationProject.financeService.dto.PayrollDTO;
import com.graduationProject.financeService.enums.PayrollStatus;
import com.graduationProject.financeService.service.PayrollService;
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
@RequestMapping("/api/v1/finance/payrolls")
@RequiredArgsConstructor
@Tag(name = "Payroll Management", description = "APIs for managing payrolls")
@PreAuthorize("hasAuthority('Finance')")
public class PayrollController {
    
    private final PayrollService payrollService;
    
    @Operation(summary = "Create a new payroll")
    @PostMapping
    public ResponseEntity<ApiResponse<PayrollDTO>> createPayroll(@Valid @RequestBody PayrollDTO dto) {
        PayrollDTO payroll = payrollService.createPayroll(dto);
        ApiResponse<PayrollDTO> response = new ApiResponse<>(
            "success",
            "Payroll created successfully",
            payroll
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all payrolls")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PayrollDTO>>> getAllPayrolls() {
        List<PayrollDTO> payrolls = payrollService.getAllPayrolls();
        ApiResponse<List<PayrollDTO>> response = new ApiResponse<>(
            "success",
            "Payrolls retrieved successfully",
            payrolls
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get payroll by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PayrollDTO>> getPayrollById(@PathVariable Long id) {
        PayrollDTO payroll = payrollService.getPayrollById(id);
        ApiResponse<PayrollDTO> response = new ApiResponse<>(
            "success",
            "Payroll retrieved successfully",
            payroll
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update payroll")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PayrollDTO>> updatePayroll(
            @PathVariable Long id,
            @Valid @RequestBody PayrollDTO dto) {
        PayrollDTO payroll = payrollService.updatePayroll(id, dto);
        ApiResponse<PayrollDTO> response = new ApiResponse<>(
            "success",
            "Payroll updated successfully",
            payroll
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete payroll")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePayroll(@PathVariable Long id) {
        payrollService.deletePayroll(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Payroll deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get payrolls by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<PayrollDTO>>> getPayrollsByStatus(
            @PathVariable PayrollStatus status) {
        List<PayrollDTO> payrolls = payrollService.getPayrollsByStatus(status);
        ApiResponse<List<PayrollDTO>> response = new ApiResponse<>(
            "success",
            "Payrolls retrieved successfully",
            payrolls
        );
        return ResponseEntity.ok(response);
    }
}

