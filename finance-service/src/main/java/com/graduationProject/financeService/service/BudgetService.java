package com.graduationProject.financeService.service;

import com.graduationProject.financeService.dto.BudgetDTO;
import com.graduationProject.financeService.exception.ResourceNotFoundException;
import com.graduationProject.financeService.model.Budget;
import com.graduationProject.financeService.repository.BudgetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Transactional
    public BudgetDTO createBudget(BudgetDTO dto) {
        Budget budget = Budget.builder()
                .name(dto.getName())
                .category(dto.getCategory())
                .allocatedAmount(dto.getAllocatedAmount())
                .spentAmount(BigDecimal.ZERO)
                .period(dto.getPeriod())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .createdAt(LocalDate.now())
                .build();

        return toDTO(budgetRepository.save(budget));
    }

    public List<BudgetDTO> getAllBudgets() {
        return budgetRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public BudgetDTO getBudgetById(Long id) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));
        return toDTO(budget);
    }

    @Transactional
    public BudgetDTO updateBudget(Long id, BudgetDTO dto) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Budget not found with id: " + id));

        budget.setName(dto.getName());
        budget.setCategory(dto.getCategory());
        budget.setAllocatedAmount(dto.getAllocatedAmount());
        budget.setPeriod(dto.getPeriod());
        budget.setStartDate(dto.getStartDate());
        budget.setEndDate(dto.getEndDate());
        budget.setDescription(dto.getDescription());
        budget.setUpdatedAt(LocalDate.now());

        return toDTO(budgetRepository.save(budget));
    }

    @Transactional
    public void deleteBudget(Long id) {
        if (!budgetRepository.existsById(id)) {
            throw new ResourceNotFoundException("Budget not found with id: " + id);
        }
        budgetRepository.deleteById(id);
    }

    private BudgetDTO toDTO(Budget budget) {
        return BudgetDTO.builder()
                .id(budget.getId())
                .name(budget.getName())
                .category(budget.getCategory())
                .allocatedAmount(budget.getAllocatedAmount())
                .spentAmount(budget.getSpentAmount())
                .period(budget.getPeriod())
                .startDate(budget.getStartDate())
                .endDate(budget.getEndDate())
                .description(budget.getDescription())
                .build();
    }
}
