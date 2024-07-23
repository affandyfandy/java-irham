package findo.lab.data.criteria;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeSearchCriteria {
    private String firstName;
    private String lastName;
    private String gender;
    private Date birthDateFrom;
    private Date birthDateTo;
    private Date hireDateFrom;
    private Date hireDateTo;
    private String title;
    private Integer minSalary;
    private Integer maxSalary;
    private String departmentName;
}
