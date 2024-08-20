package com.fpt.MidtermG1.data.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.fpt.MidtermG1.data.entity.Customer;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerSpecification implements Specification<Customer> {

    private final String keyword;

    @Override
    public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (keyword != null) {
            predicates.add(builder.like(root.get("name"), "%" + keyword + "%"));
        }

        if (keyword != null) {
            predicates.add(builder.like(root.get("phoneNumber"), "%" + keyword + "%"));
        }

        if (keyword != null) {
            predicates.add(builder.like(root.get("status"), "%" + keyword + "%"));
        }

        return builder.or(predicates.toArray(Predicate[]::new));
    }
}
