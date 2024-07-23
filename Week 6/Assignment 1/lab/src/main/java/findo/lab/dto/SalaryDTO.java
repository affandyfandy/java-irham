package findo.lab.dto;

import java.util.Date;

import findo.lab.data.entity.Salary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryDTO {
    private Integer salary;
    private Date fromDate;
    private Date toDate;

    public Salary toEntity() {
        return Salary.builder()
                .salary(this.getSalary())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}