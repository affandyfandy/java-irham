package findo.lab.dto;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class EmployeeDTO {
    private String id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be a past date")
    private Date dob;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotBlank(message = "Department is mandatory")
    private String department;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    @NotBlank(message = "Phone number is mandatory")
    private String phone;
}
