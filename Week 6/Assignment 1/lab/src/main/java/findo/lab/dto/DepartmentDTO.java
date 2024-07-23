package findo.lab.dto;

import java.util.List;

import findo.lab.data.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private String deptNo;
    private String deptName;
    private List<DeptEmpDTO> deptEmps;
    private List<DeptManagerDTO> deptManagers;

    public Department toEntity() {
        return Department.builder()
                .deptNo(this.getDeptNo())
                .deptName(this.getDeptName())
                .build();
    }
}
