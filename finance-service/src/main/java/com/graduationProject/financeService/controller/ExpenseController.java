package com.graduationProject.financeService.controller;

import com.graduationProject.financeService.dto.ApiResponse;
import com.graduationProject.financeService.dto.ExpenseDTO;
import com.graduationProject.financeService.enums.ExpenseCategory;
import com.graduationProject.financeService.service.ExpenseService;
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
@RequestMapping("/api/v1/finance/expenses")
@RequiredArgsConstructor
@Tag(name = "Expense Management", description = "APIs for managing expenses")
@PreAuthorize("hasAuthority('Finance')")
public class ExpenseController {
    
    private final ExpenseService expenseService;
    
    @Operation(summary = "Create a new expense")
    @PostMapping
    public ResponseEntity<ApiResponse<ExpenseDTO>> createExpense(@Valid @RequestBody ExpenseDTO dto) {
        ExpenseDTO expense = expenseService.createExpense(dto);
        ApiResponse<ExpenseDTO> response = new ApiResponse<>(
            "success",
            "Expense created successfully",
            expense
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all expenses")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ExpenseDTO>>> getAllExpenses() {
        List<ExpenseDTO> expenses = expenseService.getAllExpenses();
        ApiResponse<List<ExpenseDTO>> response = new ApiResponse<>(
            "success",
            "Expenses retrieved successfully",
            expenses
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get expense by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDTO>> getExpenseById(@PathVariable Long id) {
        ExpenseDTO expense = expenseService.getExpenseById(id);
        ApiResponse<ExpenseDTO> response = new ApiResponse<>(
            "success",
            "Expense retrieved successfully",
            expense
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update expense")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ExpenseDTO>> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseDTO dto) {
        ExpenseDTO expense = expenseService.updateExpense(id, dto);
        ApiResponse<ExpenseDTO> response = new ApiResponse<>(
            "success",
            "Expense updated successfully",
            expense
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete expense")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Expense deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get expenses by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<ExpenseDTO>>> getExpensesByCategory(
            @PathVariable ExpenseCategory category) {
        List<ExpenseDTO> expenses = expenseService.getExpensesByCategory(category);
        ApiResponse<List<ExpenseDTO>> response = new ApiResponse<>(
            "success",
            "Expenses retrieved successfully",
            expenses
        );
        return ResponseEntity.ok(response);
    }
}

