package com.fpt.MidtermG1.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    private int id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name can be up to 255 characters long")
    private String name;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Status is mandatory")
    private Status status;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    private List<InvoiceProductDTO> invoiceProducts;

    public Product toEntity() {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setStatus(status);
        product.setCreatedTime(createdTime);
        product.setUpdatedTime(updatedTime);
        return product;
    }
}
