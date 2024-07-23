package findo.lab.data.entity;

import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import findo.lab.dto.EmployeeDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empNo;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false, length = 14)
    private String firstName;

    @Column(nullable = false, length = 16)
    private String lastName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private Date hireDate;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<Title> titles;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<Salary> salaries;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<DeptEmp> deptEmps;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private Set<DeptManager> deptManagers;

    public enum Gender {
        M, F
    }

    public EmployeeDTO toDTO() {
        return EmployeeDTO.builder()
                .empNo(this.getEmpNo())
                .birthDate(this.getBirthDate())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .gender(this.getGender())
                .hireDate(this.getHireDate())
                .salaries(Optional.ofNullable(this.getSalaries())
                        .map(salary -> salary.stream()
                                .map(Salary::toDTO)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()))
                .titles(Optional.ofNullable(this.getTitles())
                        .map(title -> title.stream()
                                .map(Title::toDTO)
                                .collect(Collectors.toList()))
                        .orElse(Collections.emptyList()))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee that = (Employee) o;
        return empNo != null && empNo.equals(that.empNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empNo);
    }
}
