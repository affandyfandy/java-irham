package com.fpt.MidtermG1.data.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fpt.MidtermG1.dto.InvoiceProductDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "InvoiceProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(InvoiceProductId.class)
public class InvoiceProduct {
    @Id
    @Column(name = "invoice_id")
    private String invoice_id;

    @Id
    @Column(name = "product_id")
    private int product_id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", insertable = false, updatable = false)
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "created_time", nullable = false, updatable = false)
    private Timestamp createdTime;

    @Column(name = "updated_time", nullable = false)
    private Timestamp updatedTime;

    @PrePersist
    protected void onCreate() {
        this.createdTime = new Timestamp(System.currentTimeMillis());
        this.updatedTime = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = new Timestamp(System.currentTimeMillis());
    }

    public InvoiceProductDTO toDTO() {
        return InvoiceProductDTO.builder()
                .invoice(this.getInvoice() != null ? this.getInvoice().toDTO() : null)
                .product(this.getProduct() != null ? this.getProduct().toDTO() : null)
                .quantity(this.getQuantity())
                .price(this.getPrice())
                .amount(this.getAmount())
                .createdTime(this.getCreatedTime())
                .updatedTime(this.getUpdatedTime())
                .build();
    }
}
