package com.fpt.MidtermG1.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.fpt.MidtermG1.dto.RevenueReportDTO;
import com.fpt.MidtermG1.util.ExcelUtil;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.MidtermG1.dto.InvoiceDTO;
import com.fpt.MidtermG1.service.InvoiceService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceDTO> addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO createdInvoice = invoiceService.addInvoice(invoiceDTO);
        return ResponseEntity.ok(createdInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> editInvoice(@PathVariable String id, @RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO updatedInvoice = invoiceService.editInvoice(id, invoiceDTO);
        return ResponseEntity.ok(updatedInvoice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable String id) {
        InvoiceDTO invoice = invoiceService.getInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getAllInvoices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<InvoiceDTO> invoices = invoiceService.getAllInvoices(page, size);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/search")
    public ResponseEntity<List<InvoiceDTO>> getInvoicesByCriteria(
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false, defaultValue = "0") int year,
            @RequestParam(required = false, defaultValue = "0") int month,
            @RequestParam(required = false) String invoiceAmountCondition,
            @RequestParam(required = false) BigDecimal invoiceAmount,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<InvoiceDTO> invoices = invoiceService.getInvoicesByCriteria(customerId, customerName, year, month,
                invoiceAmountCondition, invoiceAmount, page, size);
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<String> exportToPDF() {
        byte[] pdfBytes = invoiceService.exportAllInvoicesToPDF();

        Path path = Paths.get("src/main/resources/invoices.pdf");
        File file = path.toFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file: " + e.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        return ResponseEntity.ok().headers(headers).body("File saved to " + path.toAbsolutePath());
    }

    @GetMapping("/export-excel")
    public ResponseEntity<String> exportInvoicesToExcel(@RequestParam(required = false) String customerId,
            @RequestParam(required = false) String customerName,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) String invoiceAmountCondition,
            @RequestParam(required = false) BigDecimal invoiceAmount) throws IOException {

        List<InvoiceDTO> invoices = invoiceService.getInvoicesByCriteria(
                customerId, customerName, year != null ? year : 0, month != null ? month : 0, invoiceAmountCondition,
                invoiceAmount, 0, Integer.MAX_VALUE);

        byte[] excelContent = ExcelUtil.exportInvoicesToExcel(invoices);

        Path path = Paths.get("src/main/resources/invoices.xlsx");
        File file = path.toFile();

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(excelContent);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        return ResponseEntity.ok().headers(headers).body("File saved to " + path.toAbsolutePath());
    }

    @GetMapping("/report")
    public List<RevenueReportDTO> getRevenueReport(@RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer day) {
        return invoiceService.getRevenueByPeriod(year, month, day);
    }

}