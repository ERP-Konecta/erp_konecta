package com.graduationProject.financeService.service;

import com.graduationProject.financeService.dto.InvoiceDTO;
import com.graduationProject.financeService.enums.InvoiceStatus;
import com.graduationProject.financeService.exception.ResourceNotFoundException;
import com.graduationProject.financeService.model.Invoice;
import com.graduationProject.financeService.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    
    private final InvoiceRepository invoiceRepository;
    
    @Transactional
    public InvoiceDTO createInvoice(InvoiceDTO dto) {
        if (invoiceRepository.findByInvoiceNumber(dto.getInvoiceNumber()).isPresent()) {
            throw new RuntimeException("Invoice number already exists");
        }
        
        Invoice invoice = Invoice.builder()
            .invoiceNumber(dto.getInvoiceNumber())
            .clientName(dto.getClientName())
            .clientEmail(dto.getClientEmail())
            .amount(dto.getAmount())
            .taxAmount(dto.getTaxAmount())
            .totalAmount(dto.getAmount().add(dto.getTaxAmount()))
            .issueDate(dto.getIssueDate())
            .dueDate(dto.getDueDate())
            .status(dto.getStatus() != null ? dto.getStatus() : InvoiceStatus.DRAFT)
            .description(dto.getDescription())
            .createdAt(LocalDate.now())
            .build();
        
        return toDTO(invoiceRepository.save(invoice));
    }
    
    public List<InvoiceDTO> getAllInvoices() {
        return invoiceRepository.findAll().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    public InvoiceDTO getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        return toDTO(invoice);
    }
    
    @Transactional
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO dto) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
        
        invoice.setClientName(dto.getClientName());
        invoice.setClientEmail(dto.getClientEmail());
        invoice.setAmount(dto.getAmount());
        invoice.setTaxAmount(dto.getTaxAmount());
        invoice.setTotalAmount(dto.getAmount().add(dto.getTaxAmount()));
        invoice.setIssueDate(dto.getIssueDate());
        invoice.setDueDate(dto.getDueDate());
        invoice.setStatus(dto.getStatus());
        invoice.setDescription(dto.getDescription());
        invoice.setUpdatedAt(LocalDate.now());
        
        return toDTO(invoiceRepository.save(invoice));
    }
    
    @Transactional
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }
    
    public List<InvoiceDTO> getInvoicesByStatus(InvoiceStatus status) {
        return invoiceRepository.findByStatus(status).stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    
    private InvoiceDTO toDTO(Invoice invoice) {
        return InvoiceDTO.builder()
            .id(invoice.getId())
            .invoiceNumber(invoice.getInvoiceNumber())
            .clientName(invoice.getClientName())
            .clientEmail(invoice.getClientEmail())
            .amount(invoice.getAmount())
            .taxAmount(invoice.getTaxAmount())
            .issueDate(invoice.getIssueDate())
            .dueDate(invoice.getDueDate())
            .status(invoice.getStatus())
            .description(invoice.getDescription())
            .build();
    }
}

