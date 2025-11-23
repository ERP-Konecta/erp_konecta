package com.graduationProject.financeService.controller;

import com.graduationProject.financeService.dto.ApiResponse;
import com.graduationProject.financeService.dto.BudgetDTO;
import com.graduationProject.financeService.service.BudgetService;
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
@RequestMapping("/api/v1/finance/budgets")
@RequiredArgsConstructor
@Tag(name = "Budget Management", description = "APIs for managing budgets")
@PreAuthorize("hasAuthority('Finance')")
public class BudgetController {
    
    private final BudgetService budgetService;
    
    @Operation(summary = "Create a new budget")
    @PostMapping
    public ResponseEntity<ApiResponse<BudgetDTO>> createBudget(@Valid @RequestBody BudgetDTO dto) {
        BudgetDTO budget = budgetService.createBudget(dto);
        ApiResponse<BudgetDTO> response = new ApiResponse<>(
            "success",
            "Budget created successfully",
            budget
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all budgets")
    @GetMapping
    public ResponseEntity<ApiResponse<List<BudgetDTO>>> getAllBudgets() {
        List<BudgetDTO> budgets = budgetService.getAllBudgets();
        ApiResponse<List<BudgetDTO>> response = new ApiResponse<>(
            "success",
            "Budgets retrieved successfully",
            budgets
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get budget by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetDTO>> getBudgetById(@PathVariable Long id) {
        BudgetDTO budget = budgetService.getBudgetById(id);
        ApiResponse<BudgetDTO> response = new ApiResponse<>(
            "success",
            "Budget retrieved successfully",
            budget
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update budget")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetDTO>> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetDTO dto) {
        BudgetDTO budget = budgetService.updateBudget(id, dto);
        ApiResponse<BudgetDTO> response = new ApiResponse<>(
            "success",
            "Budget updated successfully",
            budget
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete budget")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBudget(@PathVariable Long id) {
        budgetService.deleteBudget(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Budget deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
}

