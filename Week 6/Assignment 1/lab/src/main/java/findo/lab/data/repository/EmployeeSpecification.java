package findo.lab.data.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import findo.lab.data.criteria.EmployeeSearchCriteria;
import findo.lab.data.entity.Department;
import findo.lab.data.entity.DeptEmp;
import findo.lab.data.entity.Employee;
import findo.lab.data.entity.Employee.Gender;
import findo.lab.data.entity.Title;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {
    
    private final EmployeeSearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getFirstName() != null) {
            predicates.add(builder.like(root.get("firstName"), "%" + criteria.getFirstName() + "%"));
        }

        if (criteria.getLastName() != null) {
            predicates.add(builder.like(root.get("lastName"), "%" + criteria.getLastName() + "%"));
        }

        if (criteria.getGender() != null) {
            predicates.add(builder.equal(root.get("gender"), Gender.valueOf(criteria.getGender())));
        }

        if (criteria.getBirthDateFrom() != null || criteria.getBirthDateTo() != null) {
            if (criteria.getBirthDateFrom() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("birthDate"), criteria.getBirthDateFrom()));
            }
            if (criteria.getBirthDateTo() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("birthDate"), criteria.getBirthDateTo()));
            }
        }

        if (criteria.getHireDateFrom() != null || criteria.getHireDateTo() != null) {
            if (criteria.getHireDateFrom() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("hireDate"), criteria.getHireDateFrom()));
            }
            if (criteria.getHireDateTo() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("hireDate"), criteria.getHireDateTo()));
            }
        }

        if (criteria.getTitle() != null) {
            Join<Employee, Title> titleJoin = root.join("titles", JoinType.LEFT);
            predicates.add(builder.like(titleJoin.get("title"), "%" + criteria.getTitle() + "%"));
        }

        if (criteria.getMinSalary() != null || criteria.getMaxSalary() != null) {
            Join<Employee, Title> salaryJoin = root.join("salaries", JoinType.LEFT);
            if (criteria.getMinSalary() != null) {
                predicates.add(builder.greaterThanOrEqualTo(salaryJoin.get("salary"), criteria.getMinSalary()));
            }
            if (criteria.getMaxSalary() != null) {
                predicates.add(builder.lessThanOrEqualTo(salaryJoin.get("salary"), criteria.getMaxSalary()));
            }
        }

        if (criteria.getDepartmentName() != null) {
            Join<Employee, DeptEmp> deptEmpJoin = root.join("deptEmps", JoinType.LEFT);
            Join<DeptEmp, Department> departmentJoin = deptEmpJoin.join("department", JoinType.LEFT);
            predicates.add(builder.like(departmentJoin.get("deptName"), "%" + criteria.getDepartmentName() + "%"));
        }

        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
