package com.fpt.MidtermG1.specifications;

import com.fpt.MidtermG1.data.entity.Invoice;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class InvoiceSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public InvoiceSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public InvoiceSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Invoice> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Invoice> result = new InvoiceSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new InvoiceSpecification(params.get(i)));
        }

        return result;
    }
}