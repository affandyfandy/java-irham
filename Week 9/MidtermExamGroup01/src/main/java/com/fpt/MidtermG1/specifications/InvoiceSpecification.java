package com.fpt.MidtermG1.specifications;

import com.fpt.MidtermG1.data.entity.Customer;
import com.fpt.MidtermG1.data.entity.Invoice;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;

public class InvoiceSpecification implements Specification<Invoice> {

    private final SearchCriteria criteria;

    public InvoiceSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Invoice> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getKey().equalsIgnoreCase("invoiceAmount")) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                return builder.greaterThanOrEqualTo(root.get("invoiceAmount"), (BigDecimal) criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                return builder.lessThanOrEqualTo(root.get("invoiceAmount"), (BigDecimal) criteria.getValue());
            }
        } else if (criteria.getKey().equalsIgnoreCase("customer.id")) {
            Join<Invoice, Customer> customerJoin = root.join("customer");
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return builder.equal(customerJoin.get("id"), criteria.getValue());
            }
        } else if (criteria.getKey().equalsIgnoreCase("customer.name")) {
            Join<Invoice, Customer> customerJoin = root.join("customer");
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                return builder.like(customerJoin.get("name"), "%" + criteria.getValue() + "%");
            }
        } else if (criteria.getKey().equalsIgnoreCase("year")) {
            int year = (int) criteria.getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.DAY_OF_YEAR, 1);
            Timestamp startOfYear = new Timestamp(calendar.getTimeInMillis());

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            calendar.set(Calendar.DAY_OF_MONTH, 31);
            Timestamp endOfYear = new Timestamp(calendar.getTimeInMillis());

            return builder.between(root.get("invoiceDate"), startOfYear, endOfYear);
        } else if (criteria.getKey().equalsIgnoreCase("month")) {
            int month = (int) criteria.getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Timestamp startOfMonth = new Timestamp(calendar.getTimeInMillis());

            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            Timestamp endOfMonth = new Timestamp(calendar.getTimeInMillis());

            return builder.between(root.get("invoiceDate"), startOfMonth, endOfMonth);
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;
    }
}