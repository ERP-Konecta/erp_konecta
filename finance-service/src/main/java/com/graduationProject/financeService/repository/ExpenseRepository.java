package com.graduationProject.financeService.repository;

import com.graduationProject.financeService.model.Expense;
import com.graduationProject.financeService.enums.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(ExpenseCategory category);
    List<Expense> findByExpenseDateBetween(LocalDate start, LocalDate end);
    List<Expense> findByCreatedBy(String createdBy);
}

