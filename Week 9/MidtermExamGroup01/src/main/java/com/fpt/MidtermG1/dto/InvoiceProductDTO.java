package com.fpt.MidtermG1.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.MidtermG1.data.entity.InvoiceProduct;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceProductDTO {
    private InvoiceDTO invoice;
    private ProductDTO product;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private BigDecimal amount;

    private Timestamp createdTime;
    private Timestamp updatedTime;

    public InvoiceProduct toEntity() {
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setInvoice(Optional.ofNullable(invoice).map(InvoiceDTO::toEntity).orElse(null));
        invoiceProduct.setProduct(Optional.ofNullable(product).map(ProductDTO::toEntity).orElse(null));
        invoiceProduct.setQuantity(quantity);
        invoiceProduct.setPrice(price);
        invoiceProduct.setAmount(amount);
        invoiceProduct.setCreatedTime(createdTime);
        invoiceProduct.setUpdatedTime(updatedTime);
        return invoiceProduct;
    }
}
