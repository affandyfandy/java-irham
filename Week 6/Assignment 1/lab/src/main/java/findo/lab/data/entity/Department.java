package findo.lab.data.entity;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import findo.lab.dto.DepartmentDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="departments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {
    @Id
    @Column(name = "dept_no", length = 4, columnDefinition = "CHAR(4)")
    private String deptNo;

    @Column(name = "dept_name", length = 40)
    private String deptName;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<DeptManager> deptManagers;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<DeptEmp> deptEmps;

    public DepartmentDTO toDTO() {
        return DepartmentDTO.builder()
                .deptNo(this.getDeptNo())
                .deptName(this.getDeptName())
                .deptEmps(this.getDeptEmps().stream().map(DeptEmp::toDTO).collect(Collectors.toList()))
                .deptManagers(this.getDeptManagers().stream().map(DeptManager::toDTO).collect(Collectors.toList()))
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Department that = (Department) obj;
        return Objects.equals(deptNo, that.deptNo);
    }
}
