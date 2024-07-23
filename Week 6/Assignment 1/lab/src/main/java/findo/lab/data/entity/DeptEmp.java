package findo.lab.data.entity;

import java.util.Date;
import java.util.Objects;

import findo.lab.dto.DeptEmpDTO;
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
@Table(name="dept_emp")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(DeptEmpId.class)
public class DeptEmp {
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no", nullable = false)
    private Department department;

    @Column(nullable = false)
    private Date fromDate;

    @Column(nullable = false)
    private Date toDate;

    public DeptEmpDTO toDTO() {
        return DeptEmpDTO.builder()
                .empNo(this.getEmployee().getEmpNo())
                .deptNo(this.getDepartment().getDeptNo())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, department);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DeptEmp that = (DeptEmp) obj;
        return Objects.equals(employee, that.employee) && Objects.equals(department, that.department);
    }
}
