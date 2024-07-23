package findo.lab.data.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptEmpId implements Serializable {
    private Integer employee;
    private String department;

    @Override
    public int hashCode() {
        return Objects.hash(employee, department);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DeptEmpId that = (DeptEmpId) obj;
        return Objects.equals(employee, that.employee) && Objects.equals(department, that.department);
    }

}
