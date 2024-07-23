package findo.lab.data.entity;

import java.util.Date;
import java.util.Objects;

import findo.lab.dto.SalaryDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="salaries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(SalaryId.class)
public class Salary {
    @Id
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="emp_no")
    private Employee employee;

    @Column(nullable = false)
    private Integer salary;

    @Id
    @Column(name = "from_date")
    private Date fromDate;

    @Column(nullable = false)
    private Date toDate;

    public SalaryDTO toDTO() {
        return SalaryDTO.builder()
                .salary(this.getSalary())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salary that = (Salary) o;
        return employee != null && employee.equals(that.employee) &&
               fromDate != null && fromDate.equals(that.fromDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, fromDate);
    }
}
