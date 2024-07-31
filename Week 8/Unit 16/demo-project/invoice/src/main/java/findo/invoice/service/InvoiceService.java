package findo.invoice.service;

import java.util.List;
import java.util.Optional;

import findo.invoice.data.entity.Invoice;
import findo.invoice.dto.InvoiceDTO;

public interface InvoiceService {
    List<InvoiceDTO> getAllInvoices();
    Optional<InvoiceDTO> getInvoiceById(int id);

    Invoice createInvoiceWithFeignClient(Invoice invoice);
    Invoice createInvoiceWithRestTemplate(Invoice invoice);
    Invoice createInvoiceWithWebClient(Invoice invoice);
}
