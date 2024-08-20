package com.fpt.MidtermG1.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.MidtermG1.data.entity.Invoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDTO {
    private String id;
    private CustomerDTO customer;
    private BigDecimal invoiceAmount;
    private Timestamp invoiceDate;
    private Timestamp createdTime;
    private Timestamp updatedTime;
    private List<InvoiceProductDTO> invoiceProducts;

    public Invoice toEntity() {
        Invoice invoice = new Invoice();
        invoice.setId(id);
        invoice.setCustomer(Optional.ofNullable(customer).map(CustomerDTO::toEntity).orElse(null));
        invoice.setInvoiceAmount(invoiceAmount);
        invoice.setInvoiceDate(invoiceDate);
        invoice.setCreatedTime(createdTime);
        invoice.setUpdatedTime(updatedTime);
        invoice.setInvoiceProducts(Optional.ofNullable(invoiceProducts).orElse(Collections.emptyList()).stream()
                .map(InvoiceProductDTO::toEntity)
                .collect(Collectors.toSet()));
        return invoice;
    }

}
