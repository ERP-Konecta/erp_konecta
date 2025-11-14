package com.graduationProject.financeService.service;

import com.graduationProject.financeService.dto.PayrollDTO;
import com.graduationProject.financeService.enums.PayrollStatus;
import com.graduationProject.financeService.exception.ResourceNotFoundException;
import com.graduationProject.financeService.model.Payroll;
import com.graduationProject.financeService.repository.PayrollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayrollService {
    
    private final PayrollRepository payrollRepository;
    
    @Transactional
    public PayrollDTO createPayroll(PayrollDTO dto) {
        BigDecimal netSalary = dto.getBaseSalary()
            .add(dto.getAllowances())
            .subtract(dto.getDeductions());
        
        Payroll payroll = Payroll.builder()
            .employeeId(dto.getEmployeeId())
            .employeeName(dto.getEmployeeName())
            .employeeEmail(dto.getEmployeeEmail())
            .baseSalary(dto.getBaseSalary())
            .allowances(dto.getAllowances())
            .deductions(dto.getDeductions())
            .netSalary(netSalary)
            .payPeriodStart(dto.getPayPeriodStart())
            .payPeriodEnd(dto.getPayPeriodEnd())
            .paymentDate(dto.getPaymentDate())
            .status(dto.getStatus() != null ? dto.getStatus() : PayrollStatus.PENDING)
            .notes(dto.getNotes())
            .createdAt(LocalDate.now())
            .build();
        
        return toDTO(payrollRepository.save(payroll));
    }
    
    public List<PayrollDTO> getAllPayrolls() {
        return payrollRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public PayrollDTO getPayrollById(Long id) {
        Payroll payroll = payrollRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id: " + id));
        return toDTO(payroll);
    }
    
    @Transactional
    public PayrollDTO updatePayroll(Long id, PayrollDTO dto) {
        Payroll payroll = payrollRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Payroll not found with id: " + id));
        
        BigDecimal netSalary = dto.getBaseSalary()
            .add(dto.getAllowances())
            .subtract(dto.getDeductions());
        
        payroll.setEmployeeName(dto.getEmployeeName());
        payroll.setEmployeeEmail(dto.getEmployeeEmail());
        payroll.setBaseSalary(dto.getBaseSalary());
        payroll.setAllowances(dto.getAllowances());
        payroll.setDeductions(dto.getDeductions());
        payroll.setNetSalary(netSalary);
        payroll.setPayPeriodStart(dto.getPayPeriodStart());
        payroll.setPayPeriodEnd(dto.getPayPeriodEnd());
        payroll.setPaymentDate(dto.getPaymentDate());
        payroll.setStatus(dto.getStatus());
        payroll.setNotes(dto.getNotes());
        payroll.setUpdatedAt(LocalDate.now());
        
        return toDTO(payrollRepository.save(payroll));
    }
    
    @Transactional
    public void deletePayroll(Long id) {
        if (!payrollRepository.existsById(id)) {
            throw new ResourceNotFoundException("Payroll not found with id: " + id);
        }
        payrollRepository.deleteById(id);
    }
    
    public List<PayrollDTO> getPayrollsByStatus(PayrollStatus status) {
        return payrollRepository.findByStatus(status).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private PayrollDTO toDTO(Payroll payroll) {
        return PayrollDTO.builder()
            .id(payroll.getId())
            .employeeId(payroll.getEmployeeId())
            .employeeName(payroll.getEmployeeName())
            .employeeEmail(payroll.getEmployeeEmail())
            .baseSalary(payroll.getBaseSalary())
            .allowances(payroll.getAllowances())
            .deductions(payroll.getDeductions())
            .payPeriodStart(payroll.getPayPeriodStart())
            .payPeriodEnd(payroll.getPayPeriodEnd())
            .paymentDate(payroll.getPaymentDate())
            .status(payroll.getStatus())
            .notes(payroll.getNotes())
            .build();
    }
}

