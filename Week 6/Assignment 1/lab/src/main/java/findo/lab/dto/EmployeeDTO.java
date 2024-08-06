package findo.lab.dto;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import findo.lab.data.entity.Employee;
import findo.lab.data.entity.Employee.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Integer empNo;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date hireDate;
    private List<SalaryDTO> salaries;
    private List<TitleDTO> titles;

    public Employee toEntity() {
        return Employee.builder()
                .empNo(this.getEmpNo())
                .birthDate(this.getBirthDate())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .gender(this.getGender())
                .hireDate(this.getHireDate())
                .salaries(Optional.ofNullable(this.getSalaries())
                        .map(salary -> salary.stream()
                                .map(SalaryDTO::toEntity)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .titles(Optional.ofNullable(this.getTitles())
                        .map(title -> title.stream()
                                .map(TitleDTO::toEntity)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .build();
    }
}