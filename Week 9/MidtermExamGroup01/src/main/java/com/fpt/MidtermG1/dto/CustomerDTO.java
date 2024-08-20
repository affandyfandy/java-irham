package com.fpt.MidtermG1.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fpt.MidtermG1.common.Status;
import com.fpt.MidtermG1.data.entity.Customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDTO {
    private String id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name can be up to 255 characters long")
    private String name;

    @NotBlank(message = "Phone number is mandatory")
    @Size(max = 20, message = "Phone number can be up to 20 characters long")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotNull(message = "Status is mandatory")
    private Status status;
    private Timestamp createdTime;
    private Timestamp updatedTime;

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(name);
        customer.setPhoneNumber(phoneNumber);
        customer.setStatus(status);
        customer.setCreatedTime(createdTime);
        customer.setUpdatedTime(updatedTime);
        return customer;
    }
}
