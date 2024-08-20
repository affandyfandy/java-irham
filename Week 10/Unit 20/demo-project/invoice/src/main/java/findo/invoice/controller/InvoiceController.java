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

    @GetMapping("/feign")
    public ResponseEntity<List<InvoiceDTO>> getInvoiceListWithFeignClient() {
        return ResponseEntity.ok(invoiceService.getAllInvoicesWithFeignClient());
    }

    @GetMapping("/feign/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceByIdWithFeignClient(@PathVariable int id) {
        Optional<InvoiceDTO> invoice = invoiceService.getInvoiceByIdWithFeignClient(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/feign")
    public Invoice createInvoiceWithFeignClient(@RequestBody Invoice invoice) {
        return invoiceService.createInvoiceWithFeignClient(invoice);
    }

    @GetMapping("/rest-template")
    public ResponseEntity<List<InvoiceDTO>> getInvoiceListWithRestTemplate() {
        return ResponseEntity.ok(invoiceService.getAllInvoicesWithRestTemplate());
    }

    @GetMapping("/rest-template/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceByIdWithRestTemplate(@PathVariable int id) {
        Optional<InvoiceDTO> invoice = invoiceService.getInvoiceByIdWithRestTemplate(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/rest-template")
    public Invoice createInvoiceWithRestTemplate(@RequestBody Invoice invoice) {
        return invoiceService.createInvoiceWithRestTemplate(invoice);
    }

    @GetMapping("/web-client")
    public ResponseEntity<List<InvoiceDTO>> getInvoiceListWithWebClient() {
        return ResponseEntity.ok(invoiceService.getAllInvoicesWithWebClient());
    }

    @GetMapping("/web-client/{id}")
    public ResponseEntity<InvoiceDTO> getInvoiceByIdWithWebClient(@PathVariable int id) {
        Optional<InvoiceDTO> invoice = invoiceService.getInvoiceByIdWithWebClient(id);
        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/web-client")
    public Invoice createInvoiceWithWebClient(@RequestBody Invoice invoice) {
        return invoiceService.createInvoiceWithWebClient(invoice);
    }
}
