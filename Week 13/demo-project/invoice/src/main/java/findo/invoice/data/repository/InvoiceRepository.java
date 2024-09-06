package findo.invoice.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import findo.invoice.data.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
