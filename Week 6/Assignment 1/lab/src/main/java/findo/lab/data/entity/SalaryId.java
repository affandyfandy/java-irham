package findo.lab.data.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryId implements Serializable {
    private Integer employee;
    private Date fromDate;

    @Override
    public int hashCode() {
        return Objects.hash(employee, fromDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SalaryId that = (SalaryId) obj;
        return Objects.equals(employee, that.employee) && Objects.equals(fromDate, that.fromDate);
    }
}