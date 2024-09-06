package findo.invoice.dto;

import java.util.List;

import lombok.Data;

@Data
public class InvoiceDTO {
    private int id;
    private List<ProductDTO> products;
    private Double totalAmount;
}
