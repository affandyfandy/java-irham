package findo.invoice.service;

import java.util.List;
import java.util.Optional;

import findo.invoice.data.entity.Invoice;
import findo.invoice.dto.InvoiceDTO;

public interface InvoiceService {
    List<InvoiceDTO> getAllInvoicesWithFeignClient();
    Optional<InvoiceDTO> getInvoiceByIdWithFeignClient(int id);
    Invoice createInvoiceWithFeignClient(Invoice invoice);

    List<InvoiceDTO> getAllInvoicesWithRestTemplate();
    Optional<InvoiceDTO> getInvoiceByIdWithRestTemplate(int id);
    Invoice createInvoiceWithRestTemplate(Invoice invoice);

    List<InvoiceDTO> getAllInvoicesWithWebClient();
    Optional<InvoiceDTO> getInvoiceByIdWithWebClient(int id);
    Invoice createInvoiceWithWebClient(Invoice invoice);
}
