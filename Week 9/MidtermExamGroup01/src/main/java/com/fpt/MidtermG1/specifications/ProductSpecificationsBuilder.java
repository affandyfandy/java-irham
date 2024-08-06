package com.fpt.MidtermG1.specifications;

import com.fpt.MidtermG1.data.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecificationsBuilder {
    private final List<SearchCriteria> params;

    public ProductSpecificationsBuilder() {
        params = new ArrayList<>();
    }

    public ProductSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Product> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<Product> result = new ProductSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new ProductSpecification(params.get(i)));
        }

        return result;
    }
}
