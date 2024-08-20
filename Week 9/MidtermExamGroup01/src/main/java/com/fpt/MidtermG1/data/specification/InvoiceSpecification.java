package com.fpt.MidtermG1.data.specification;

import com.fpt.MidtermG1.data.entity.Invoice;
import org.springframework.data.jpa.domain.Specification;

public class InvoiceSpecification {

    public static Specification<Invoice> hasCustomerId(String customerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("customer").get("id"), customerId);
    }

    public static Specification<Invoice> hasInvoiceYear(int year) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("invoiceDate")), year);
    }

    public static Specification<Invoice> hasInvoiceMonth(int month) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.function("MONTH", Integer.class, root.get("invoiceDate")), month);
    }
}
