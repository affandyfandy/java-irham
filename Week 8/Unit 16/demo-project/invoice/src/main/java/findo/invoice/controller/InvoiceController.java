package findo.invoice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import findo.invoice.data.entity.Invoice;
import findo.invoice.dto.InvoiceDTO;
import findo.invoice.service.InvoiceService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    
    final private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> getInvoiceList() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceById(@PathVariable int id) {
        Optional<InvoiceDTO> invoice = invoiceService.getInvoiceById(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/feign")
    public Invoice createInvoiceWithFeignClient(@RequestBody Invoice invoice) {
        return invoiceService.createInvoiceWithFeignClient(invoice);
    }

    @PostMapping("/rest-template")
    public Invoice createInvoiceWithRestTemplate(@RequestBody Invoice invoice) {
        return invoiceService.createInvoiceWithRestTemplate(invoice);
    }

    @PostMapping("/web-client")
    public Invoice createInvoiceWithWebClient(@RequestBody Invoice invoice) {
        return invoiceService.createInvoiceWithWebClient(invoice);
    }
}
