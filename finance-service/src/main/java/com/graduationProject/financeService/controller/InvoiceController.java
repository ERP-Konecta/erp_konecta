package com.graduationProject.financeService.controller;

import com.graduationProject.financeService.dto.ApiResponse;
import com.graduationProject.financeService.dto.InvoiceDTO;
import com.graduationProject.financeService.enums.InvoiceStatus;
import com.graduationProject.financeService.service.InvoiceService;
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
@RequestMapping("/api/v1/finance/invoices")
@RequiredArgsConstructor
@Tag(name = "Invoice Management", description = "APIs for managing invoices")
@PreAuthorize("hasAuthority('Finance')")
public class InvoiceController {
    
    private final InvoiceService invoiceService;
    
    @Operation(summary = "Create a new invoice")
    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceDTO>> createInvoice(@Valid @RequestBody InvoiceDTO dto) {
        InvoiceDTO invoice = invoiceService.createInvoice(dto);
        ApiResponse<InvoiceDTO> response = new ApiResponse<>(
            "success",
            "Invoice created successfully",
            invoice
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @Operation(summary = "Get all invoices")
    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceDTO>>> getAllInvoices() {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices();
        ApiResponse<List<InvoiceDTO>> response = new ApiResponse<>(
            "success",
            "Invoices retrieved successfully",
            invoices
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get invoice by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceDTO>> getInvoiceById(@PathVariable Long id) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(id);
        ApiResponse<InvoiceDTO> response = new ApiResponse<>(
            "success",
            "Invoice retrieved successfully",
            invoice
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Update invoice")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceDTO>> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDTO dto) {
        InvoiceDTO invoice = invoiceService.updateInvoice(id, dto);
        ApiResponse<InvoiceDTO> response = new ApiResponse<>(
            "success",
            "Invoice updated successfully",
            invoice
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Delete invoice")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        ApiResponse<Void> response = new ApiResponse<>(
            "success",
            "Invoice deleted successfully"
        );
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get invoices by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<InvoiceDTO>>> getInvoicesByStatus(
            @PathVariable InvoiceStatus status) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByStatus(status);
        ApiResponse<List<InvoiceDTO>> response = new ApiResponse<>(
            "success",
            "Invoices retrieved successfully",
            invoices
        );
        return ResponseEntity.ok(response);
    }
}

