package com.fpt.MidtermG1.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductId implements Serializable {
    private String invoice_id;
    private int product_id;
}
