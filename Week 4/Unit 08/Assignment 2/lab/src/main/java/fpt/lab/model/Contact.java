package fpt.lab.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    private String id;
    private String name;
    private Integer age;
    private Date dob;
    private String email;
}
