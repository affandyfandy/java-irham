package findo.lab.dto;

import java.util.Date;

import findo.lab.data.entity.Department;
import findo.lab.data.entity.DeptManager;
import findo.lab.data.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptManagerDTO {
    private String deptNo;
    private Integer empNo;
    private Date fromDate;
    private Date toDate;

    public DeptManager toEntity(Department department, Employee employee) {
        return DeptManager.builder()
                .department(department)
                .employee(employee)
                .fromDate(this.fromDate)
                .toDate(this.toDate)
                .build();
    }
}