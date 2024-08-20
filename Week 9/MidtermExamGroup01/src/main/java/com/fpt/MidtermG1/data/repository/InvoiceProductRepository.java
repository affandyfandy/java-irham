package com.fpt.MidtermG1.data.repository;

import com.fpt.MidtermG1.data.entity.Invoice;
import com.fpt.MidtermG1.data.entity.InvoiceProduct;
import com.fpt.MidtermG1.data.entity.InvoiceProductId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, InvoiceProductId> {

    @Transactional
    @Modifying
    @Query("DELETE FROM InvoiceProduct ip WHERE ip.invoice = :invoice")
    void deleteByInvoice(@Param("invoice") Invoice invoice);

    @Query("SELECT ip FROM InvoiceProduct ip JOIN FETCH ip.invoice i JOIN FETCH i.customer c JOIN FETCH ip.product p")
    List<InvoiceProduct> findAllWithDetails();
}
