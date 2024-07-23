package findo.lab.data.entity;

import java.util.Date;
import java.util.Objects;

import findo.lab.dto.DeptManagerDTO;
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
@Table(name="dept_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(DeptManagerId.class)
public class DeptManager {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_no", nullable = false)
    private Department department;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private Date fromDate;

    @Column(nullable = false)
    private Date toDate;

    public DeptManagerDTO toDTO() {
        return DeptManagerDTO.builder()
                .deptNo(this.getDepartment().getDeptNo())
                .empNo(this.getEmployee().getEmpNo())
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
        DeptManager that = (DeptManager) obj;
        return Objects.equals(employee, that.employee) && Objects.equals(department, that.department);
    }
}
