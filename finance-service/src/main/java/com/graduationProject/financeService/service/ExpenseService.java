package com.graduationProject.financeService.service;

import com.graduationProject.financeService.dto.ExpenseDTO;
import com.graduationProject.financeService.enums.ExpenseCategory;
import com.graduationProject.financeService.exception.ResourceNotFoundException;
import com.graduationProject.financeService.model.Expense;
import com.graduationProject.financeService.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    
    @Transactional
    public ExpenseDTO createExpense(ExpenseDTO dto) {
        String createdBy = SecurityContextHolder.getContext().getAuthentication().getName();
        
        Expense expense = Expense.builder()
            .title(dto.getTitle())
            .category(dto.getCategory())
            .amount(dto.getAmount())
            .expenseDate(dto.getExpenseDate())
            .description(dto.getDescription())
            .createdBy(createdBy)
            .createdAt(LocalDate.now())
            .build();
        
        return toDTO(expenseRepository.save(expense));
    }
    
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public ExpenseDTO getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return toDTO(expense);
    }
    
    @Transactional
    public ExpenseDTO updateExpense(Long id, ExpenseDTO dto) {
        Expense expense = expenseRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        
        expense.setTitle(dto.getTitle());
        expense.setCategory(dto.getCategory());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setDescription(dto.getDescription());
        expense.setUpdatedAt(LocalDate.now());
        
        return toDTO(expenseRepository.save(expense));
    }
    
    @Transactional
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }
    
    public List<ExpenseDTO> getExpensesByCategory(ExpenseCategory category) {
        return expenseRepository.findByCategory(category).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private ExpenseDTO toDTO(Expense expense) {
        return ExpenseDTO.builder()
            .id(expense.getId())
            .title(expense.getTitle())
            .category(expense.getCategory())
            .amount(expense.getAmount())
            .expenseDate(expense.getExpenseDate())
            .description(expense.getDescription())
            .build();
    }
}

