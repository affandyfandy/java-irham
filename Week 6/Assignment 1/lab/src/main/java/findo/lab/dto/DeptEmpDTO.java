package findo.lab.dto;

import java.util.Date;

import findo.lab.data.entity.Department;
import findo.lab.data.entity.DeptEmp;
import findo.lab.data.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeptEmpDTO {
    private Integer empNo;
    private String deptNo;
    private Date fromDate;
    private Date toDate;

    public DeptEmp toEntity(Employee employee, Department department) {
        return DeptEmp.builder()
                .employee(employee)
                .department(department)
                .fromDate(this.fromDate)
                .toDate(this.toDate)
                .build();
    }
}
